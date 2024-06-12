package com.medicine.medicine_back.service.implement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.dto.response.medicinePermission.GetMedicinePermissionResponseDto;
import com.medicine.medicine_back.dto.response.medicinePermission.MedicinePermissionResponseDto;
import com.medicine.medicine_back.entity.MedicinePermissionEntity;
import com.medicine.medicine_back.repository.MedicinePermissionRepository;
import com.medicine.medicine_back.repository.resultSet.GetMedicinePermissionResultSet;
import com.medicine.medicine_back.service.MedicinePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                URI uri = null;
                try {
                    uri = new URI(UriComponentsBuilder
                            .fromUriString("https://apis.data.go.kr/1471000/DrugPrdtPrmsnInfoService05/getDrugPrdtPrmsnDtlInq04?")
                            .queryParam("serviceKey", apiKey)
                            .queryParam("pageNo", pageNo)
                            .queryParam("numOfRows", 100)
                            .queryParam("type", "json")
                            .build()
                            .toUriString());
                    System.out.println(uri);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }

                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-type", "application/json; charset=UTF-8");

                HttpEntity<Map<String,String>> requestMessage = new HttpEntity<>(headers);

                ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, requestMessage,
                        String.class);
                String apiTest = response.getBody();
//                System.out.println(apiTest);


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
        GetMedicinePermissionResultSet resultSet = null;
        try {
            resultSet = medicinePermissionRepository.getMedicinePermission(ITEM_SEQ);
        } catch (Exception exception) {
            return ResponseDto.databaseError();
        }
        return GetMedicinePermissionResponseDto.success(resultSet);
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
