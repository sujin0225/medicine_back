package com.medicine.medicine_back.service.implement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.dto.response.medicine.GetMedicineListResponseDto;
import com.medicine.medicine_back.dto.response.medicine.GetMedicineResponseDto;
import com.medicine.medicine_back.dto.response.medicine.MedicineResponseDto;
import com.medicine.medicine_back.entity.MedicineEntity;
import com.medicine.medicine_back.repository.MedicineListRepository;
import com.medicine.medicine_back.repository.MedicineRepository;
import com.medicine.medicine_back.repository.resultSet.GetMedicineResultSet;
import com.medicine.medicine_back.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.print.Pageable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MedicineServiceImplement implements MedicineService {
    private final MedicineRepository medicineRepository;
    private final MedicineListRepository medicineListRepository;

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
                URI uri = null;
                try {
                    uri = new URI(UriComponentsBuilder
                            .fromUriString("http://apis.data.go.kr/1471000/MdcinGrnIdntfcInfoService01/getMdcinGrnIdntfcInfoList01")
                            .queryParam("serviceKey", apiKey)
                            .queryParam("type", "json")
                            .queryParam("pageNo", pageNo)
                            .queryParam("numOfRows", "300")
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

    //의약품 정보 불러오기
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

    //의약품 리스트 불러오기
    public ResponseEntity<? super GetMedicineListResponseDto> getMedicineList(int page, int pageSize, String item_name) {
        try {
            PageRequest pageable = PageRequest.of(page, pageSize);
            Page<MedicineEntity> medicinePage = medicineListRepository.findAll(pageable);
            List<MedicineEntity> medicineEntities = medicinePage.getContent();
            int totalPages = medicinePage.getTotalPages();
            int totalCounts = (int) medicinePage.getTotalElements();

//            return ResponseEntity.ok(GetMedicineListResponseDto.success(medicineEntities, page, totalPages, totalCounts));
            return GetMedicineListResponseDto.success(medicineEntities, page, totalPages, totalCounts, item_name);
        } catch (Exception exception) {
            return ResponseDto.databaseError();
        }

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
