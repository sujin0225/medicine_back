package com.medicine.medicine_back.service.implement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicine.medicine_back.dto.response.medicineInfo.GetMedicineInfoResponseDto;
import com.medicine.medicine_back.dto.response.medicineInfo.MedicineInfoResponseDto;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.entity.MedicineInfoEntity;
import com.medicine.medicine_back.repository.MedicineInfoRepository;
import com.medicine.medicine_back.repository.resultSet.GetMedicineInfoResultSet;
import com.medicine.medicine_back.service.MedicineInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
public class MedicineInfoServiceImplement implements MedicineInfoService {
    private final MedicineInfoRepository medicineInfoRepository;

    @Value("${medicine.api.key}")
    private String apiKey;

    //의약품 복약 정보 db에 저장
    @Override
    public ResponseEntity<? super MedicineInfoResponseDto> getMedicineInfoAPI() {
        int pageNo = 1;
        boolean isLastPage = false;
        try {
            int totalRecords = 0;

            while (!isLastPage) {
                URI uri = null;
                try {
                    uri = new URI(UriComponentsBuilder
                            .fromUriString("http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList?")
                            .queryParam("serviceKey", apiKey)
                            .queryParam("pageNo", pageNo)
                            .queryParam("type", "json")
                            .queryParam("numOfRows", 100)
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

                if (totalRecords == 0) {
                    totalRecords = parseTotalRecords(response.getBody());
                }

                // API 응답 파싱
                String responseBody = response.getBody();
                List<MedicineInfoEntity> resultSets = parseMedicineInfo(responseBody);
                System.out.println("결과 리스트: " + resultSets);

                // 데이터베이스에 저장
                medicineInfoRepository.saveAll(resultSets);

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
        return MedicineInfoResponseDto.success();
    }

    //의약품 복약 정보 불러오기
    @Override
    public ResponseEntity<? super GetMedicineInfoResponseDto> getMedicineInfo(String ITEM_SEQ) {
        GetMedicineInfoResultSet resultSet = null;
        try {
            resultSet = medicineInfoRepository.getMedicineInfo(ITEM_SEQ);
        } catch (Exception exception) {
            return ResponseDto.databaseError();
        }
        return GetMedicineInfoResponseDto.success(resultSet);
    }

    //외부 API 응답 파싱
    private List<MedicineInfoEntity> parseMedicineInfo(String body){
        ObjectMapper objectMapper = new ObjectMapper();
        List<MedicineInfoEntity> Medicine = new ArrayList<>();

        try {
            // JSON 트리를 읽어들임
            JsonNode root = objectMapper.readTree(body);

            // "body" 노드에 접근
            JsonNode localDataNode = root.path("body");

            // "items" 배열에 접근
            JsonNode rowsNode = localDataNode.path("items");

            // "items" 배열의 각 요소를 순회하면서 MedicineEntity 객체로 변환하여 리스트에 추가
            for (JsonNode itemNode : rowsNode) {
                MedicineInfoEntity medicine = objectMapper.treeToValue(itemNode, MedicineInfoEntity.class);
                Medicine.add(medicine);
            }
//            System.out.println(rowsNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Medicine;
    }

    //전체 데이터 수
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
