package com.medicine.medicine_back.service.implement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicine.medicine_back.common.CoordinateConverter;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.dto.response.medicineStore.MedicineStoreResponseDto;
import com.medicine.medicine_back.entity.MedicineStoreEntity;
import com.medicine.medicine_back.repository.MedicineStoreRepository;
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

    @Value("${medicine.store.api.key}")
    private String apiKey;

    //상비의약품 API 호출
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

    //외부 API 응답 파싱
    private List<MedicineStoreEntity> parsePharmacies(String body){
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
                    pharmacy.setX(String.valueOf(wgs84[0]));
                    pharmacy.setY(String.valueOf(wgs84[1]));
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
