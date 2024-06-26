package com.medicine.medicine_back.service.implement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicine.medicine_back.common.CoordinateConverter;
import com.medicine.medicine_back.common.GeocodingService;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.dto.response.medicineStore.GetMedicineStoreListResponseDto;
import com.medicine.medicine_back.dto.response.medicineStore.MedicineStoreResponseDto;
import com.medicine.medicine_back.entity.MedicineStoreEntity;
import com.medicine.medicine_back.repository.MedicineStoreRepository;
import com.medicine.medicine_back.repository.resultSet.GetMedicineStoreResultSet;
import com.medicine.medicine_back.service.MedicineStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class MedicineStoreServiceImplement implements MedicineStoreService {
    private final MedicineStoreRepository medicineStoreRepository;
    private final GeocodingService geocodingService;

    @Value("${medicine.store.api.key}")
    private String apiKey;

    //상비의약품 API 호출 및 데이터 DB 저장
    public ResponseEntity<? super MedicineStoreResponseDto> getMedicineStoreAPI(){
        final int MAX_RECORDS_PER_REQUEST = 1000;
        int start = 1;
        int end = start + MAX_RECORDS_PER_REQUEST - 1;

        boolean isLastPage = false;
        try {
            int totalRecords = 0;

            while (!isLastPage) {
                String url = String.format("http://openapi.seoul.go.kr:8088/%s/json/LOCALDATA_010105/%d/%d/", apiKey, start, end);
                System.out.println("요청 URL: " + url);
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

                if (totalRecords == 0) {
                    totalRecords = parseTotalRecords(response.getBody());
                }

                // API 응답 파싱
                String responseBody = response.getBody();
                List<MedicineStoreEntity> resultSets = parsePharmacies(responseBody);
                System.out.println("결과 리스트: " + resultSets);

                // 데이터베이스에 저장
                medicineStoreRepository.saveAll(resultSets);

                start += MAX_RECORDS_PER_REQUEST;
                if (start > totalRecords) { // 전체 레코드 수를 넘어가면 반복 중지
                    break;
                }
                end = start + MAX_RECORDS_PER_REQUEST - 1;
            }
        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return MedicineStoreResponseDto.success();
    }

    //사용자 위치 주변 상비 의약품 판매처
    @Override
    public List<MedicineStoreEntity> findClosestStores(double X, double Y) {
        // 위도(Y)와 경도(X)의 유효성 검사
        if (Y < -90 || Y > 90 || X < -180 || X > 180) {
            throw new IllegalArgumentException("위도는 -90과 90 사이, 경도는 -180과 180 사이의 값이어야 합니다.");
        }
        // X와 Y를 바꾸어 호출
        return medicineStoreRepository.findClosest(Y, X);
    }

    //상비의약품 판매처 지역으로 검색
    @Override
    public ResponseEntity<? super GetMedicineStoreListResponseDto> findAreaStore(String address, double radius) {
        List<GetMedicineStoreResultSet> resultSet = new ArrayList<>();
        try {
            // 주소를 위도와 경도로 변환
            double[] coordinates = geocodingService.getCoordinates(address);
            double latitude = coordinates[0];
            double longitude = coordinates[1];
            resultSet = medicineStoreRepository.findWithinRadius(latitude, longitude, radius);
            System.out.println("lat "+latitude);
            System.out.println("lon "+longitude);
            // 반경 내의 약국 찾기
//            medicineStoreRepository.findWithinRadius(latitude, longitude, radius);
        } catch (Exception exception) {
            return ResponseDto.databaseError();
        }
        return GetMedicineStoreListResponseDto.success(resultSet);
    }

    //외부 API 응답 파싱
    private List<MedicineStoreEntity> parsePharmacies(String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<MedicineStoreEntity> pharmacies = new ArrayList<>();
        CoordinateConverter coordinateConverter = new CoordinateConverter();

        try {
            // JSON 트리를 읽어들임
            JsonNode root = objectMapper.readTree(body);

            // "LOCALDATA_010105" 노드에 접근
            JsonNode localDataNode = root.path("LOCALDATA_010105");

            // "row" 배열에 접근
            JsonNode rowsNode = localDataNode.path("row");

            // "row" 배열을 순회하며 영업 중인 약국만 필터링
            for (JsonNode row : rowsNode) {
                MedicineStoreEntity pharmacy = objectMapper.treeToValue(row, MedicineStoreEntity.class);

                String xStr = pharmacy.getX();
                String yStr = pharmacy.getY();

                // xStr과 yStr이 null이 아니고 비어있지 않은 경우에만 convertTMToWGS84 메소드를 호출
                if (xStr != null && !xStr.isEmpty() && yStr != null && !yStr.isEmpty()) {
                    double[] wgs84 = coordinateConverter.convertTMToWGS84(xStr, yStr);
                    System.out.println("Converted latitude: " + wgs84[0] + ", longitude: " + wgs84[1]);
                    pharmacy.setX(String.valueOf(wgs84[1])); // 경도(longitude)
                    pharmacy.setY(String.valueOf(wgs84[0])); // 위도(latitude)
                }

                if ("영업중".equals(pharmacy.getDTLSTATENM())) {
                    pharmacies.add(pharmacy);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pharmacies;
    }

    //전체 게시글 수
    private int parseTotalRecords(String responseBody) {
    ObjectMapper objectMapper = new ObjectMapper();
    int totalRecords = 0;

    try {
        // JSON 트리를 읽어들임
        JsonNode root = objectMapper.readTree(responseBody);

        // "LOCALDATA_010105" 노드에 접근
        JsonNode localDataNode = root.path("LOCALDATA_010105");

        // "list_total_count" 값을 가져옴
        totalRecords = localDataNode.path("list_total_count").asInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalRecords;
    }
}