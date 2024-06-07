package com.medicine.medicine_back.service.implement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.dto.response.medicinePermission.GetMedicinePermissionResponseDto;
import com.medicine.medicine_back.dto.response.medicinePermission.MedicinePermissionResponseDto;
import com.medicine.medicine_back.entity.MedicineEntity;
import com.medicine.medicine_back.entity.MedicinePermissionEntity;
import com.medicine.medicine_back.repository.MedicinePermissionRepository;
import com.medicine.medicine_back.service.MedicinePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicinePermissionServiceImplement implements MedicinePermissionService {
    private final MedicinePermissionRepository medicinePermissionRepository;

    @Value("${medicine.api.key}")
    private String apiKey;

    //의약품 제품 허가정보 API 호출 및 데이터 DB 저장
    @Override
    public ResponseEntity<? super MedicinePermissionResponseDto> getMedicinePermissionAPI() {
        int pageNo = 1;
        boolean isLastPage = false;
        try {
            int totalRecords = 0;

            while (!isLastPage) {
//                String originalString = apiKey;
//                System.out.println(apiKey);
//                String encodedString = URLEncoder.encode(originalString, "UTF-8");
//                System.out.println("Encoded URL: " + encodedString);
//
//                final String baseUrl = "https://apis.data.go.kr/1471000/DrugPrdtPrmsnInfoService05/getDrugPrdtPrmsnDtlInq04?servicekey=" + encodedString + "&type=json";
//                String url = baseUrl + "&pageNo=" + pageNo + "&numOfRows=100";
//                System.out.println("요청 URL: " + url);
//                RestTemplate restTemplate = new RestTemplate();
//                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);


                UriComponentsBuilder builder = UriComponentsBuilder
                        .fromHttpUrl("https://apis.data.go.kr/1471000/DrugPrdtPrmsnInfoService05/getDrugPrdtPrmsnDtlInq04?")
                        .queryParam("serviceKey", apiKey)
                        .queryParam("type", "json")
                        .queryParam("pageNo", pageNo)
                        .queryParam("numOfRows", "100");
                String url = builder.toUriString();
                System.out.println(url);

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);



                if (totalRecords == 0) {
                    totalRecords = parseTotalRecords(response.getBody());
                }

                // API 응답 파싱
                String responseBody = response.getBody();
                List<MedicinePermissionEntity> resultSets = parseMedicinePermission(responseBody);
                System.out.println("결과 리스트: " + resultSets);

                // 데이터베이스에 저장
                medicinePermissionRepository.saveAll(resultSets);

                // 마지막 페이지 확인
                if (resultSets.size() < 100) {
                    isLastPage = true;
                } else {
                    pageNo++; // 페이지 번호 증가
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return MedicinePermissionResponseDto.success();
    }

    //의약품 제품 허가정보 불러오기
    @Override
    public ResponseEntity<? super GetMedicinePermissionResponseDto> getMedicinePermission(String ITEM_SEQ) {
        return null;
    }

    //외부 API 응답 파싱
    private List<MedicinePermissionEntity> parseMedicinePermission(String body){
        ObjectMapper objectMapper = new ObjectMapper();
        List<MedicinePermissionEntity> Medicine = new ArrayList<>();

        try {
            // JSON 트리를 읽어들임
            JsonNode root = objectMapper.readTree(body);

            // "body" 노드에 접근
            JsonNode localDataNode = root.path("body");

            // "items" 배열에 접근
            JsonNode rowsNode = localDataNode.path("items");

            // "items" 배열의 각 요소를 순회하면서 MedicineEntity 객체로 변환하여 리스트에 추가
            for (JsonNode itemNode : rowsNode) {
                MedicinePermissionEntity medicine = objectMapper.treeToValue(itemNode, MedicinePermissionEntity.class);
                Medicine.add(medicine);
            }
//            System.out.println(rowsNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Medicine;
    }

    //전체 게시글 수
    private int parseTotalRecords(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        int totalRecords = 0;

        try {
            // JSON 트리를 읽어들임
            JsonNode root = objectMapper.readTree(responseBody);

            // "LOCALDATA_010105" 노드에 접근
            JsonNode localDataNode = root.path("body");

            // "list_total_count" 값을 가져옴
            totalRecords = localDataNode.path("totalCount").asInt();
            System.out.println("totalRecords 값: "+totalRecords);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalRecords;
    }
}
