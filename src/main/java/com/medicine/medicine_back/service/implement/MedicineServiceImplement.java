package com.medicine.medicine_back.service.implement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.dto.response.medicine.GetMedicineResponseDto;
import com.medicine.medicine_back.dto.response.medicine.MedicineResponseDto;
import com.medicine.medicine_back.entity.MedicineEntity;
import com.medicine.medicine_back.repository.MedicineRepository;
import com.medicine.medicine_back.repository.resultSet.GetMedicineResultSet;
import com.medicine.medicine_back.service.MedicineService;
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
public class MedicineServiceImplement implements MedicineService {
    private final MedicineRepository medicineRepository;

    @Value("${medicine.api.key}")
    private String apiKey;

    //의약품 API 호출 및 데이터 DB 저장
    @Override
    public ResponseEntity<? super MedicineResponseDto> getMedicineAPI() {

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
//                final String baseUrl = "http://apis.data.go.kr/1471000/MdcinGrnIdntfcInfoService01/getMdcinGrnIdntfcInfoList01?" + apiKey + "&type=json&item_name=&entp_name=&item_seq=&img_regist_ts=";
//                String url = baseUrl + "&pageNo=" + pageNo + "&numOfRows=300&edi_code=";
//                System.out.println("요청 URL: " + url);
//                RestTemplate restTemplate = new RestTemplate();
//                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

                UriComponentsBuilder builder = UriComponentsBuilder
                        .fromHttpUrl("http://apis.data.go.kr/1471000/MdcinGrnIdntfcInfoService01/getMdcinGrnIdntfcInfoList01")
                        .queryParam("serviceKey", apiKey)
                        .queryParam("type", "json")
                        .queryParam("item_name", "")
                        .queryParam("entp_name", "")
                        .queryParam("item_seq", "")
                        .queryParam("img_regist_ts", "")
                        .queryParam("pageNo", pageNo)
                        .queryParam("numOfRows", "300")
                        .queryParam("edi_code", "");
                String url = builder.toUriString();
                System.out.println(url);

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);


                if (totalRecords == 0) {
                    totalRecords = parseTotalRecords(response.getBody());
                }

                // API 응답 파싱
                String responseBody = response.getBody();
                List<MedicineEntity> resultSets = parseMedicine(responseBody);
                System.out.println("결과 리스트: " + resultSets);

                // 데이터베이스에 저장
                medicineRepository.saveAll(resultSets);

                // 마지막 페이지 확인
                if (resultSets.size() < 300) {
                    isLastPage = true;
                } else {
                    pageNo++; // 페이지 번호 증가
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return MedicineResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetMedicineResponseDto> getMedicine(String ITEM_SEQ) {
        GetMedicineResultSet resultSet = null;

        try {
            resultSet = medicineRepository.getMedicine(ITEM_SEQ);
        } catch (Exception exception) {
            return ResponseDto.databaseError();
        }
        return GetMedicineResponseDto.success(resultSet);
    }

    //외부 API 응답 파싱
    private List<MedicineEntity> parseMedicine(String body){
        ObjectMapper objectMapper = new ObjectMapper();
        List<MedicineEntity> Medicine = new ArrayList<>();

        try {
            // JSON 트리를 읽어들임
            JsonNode root = objectMapper.readTree(body);

            // "body" 노드에 접근
            JsonNode localDataNode = root.path("body");

            // "items" 배열에 접근
            JsonNode rowsNode = localDataNode.path("items");

            // "items" 배열의 각 요소를 순회하면서 MedicineEntity 객체로 변환하여 리스트에 추가
            for (JsonNode itemNode : rowsNode) {
                MedicineEntity medicine = objectMapper.treeToValue(itemNode, MedicineEntity.class);
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
