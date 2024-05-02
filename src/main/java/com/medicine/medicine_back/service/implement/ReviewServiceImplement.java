package com.medicine.medicine_back.service.implement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicine.medicine_back.dto.object.ReviewListItem;
import com.medicine.medicine_back.dto.request.review.PostReviewRequestDto;
//import com.medicine.medicine_back.dto.response.review.GetReviewResponseDto;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.dto.response.review.GetReviewResponseDto;
import com.medicine.medicine_back.dto.response.review.PostReviewResponseDto;
import com.medicine.medicine_back.entity.ReviewEntity;
import com.medicine.medicine_back.repository.ImageRepository;
import com.medicine.medicine_back.repository.ReviewRepository;
//import com.medicine.medicine_back.repository.resultSet.GetReviewResultSet;
import com.medicine.medicine_back.repository.resultSet.GetReviewResultSet;
import com.medicine.medicine_back.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImplement implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    private final RestTemplate restTemplate;

    @Value("${medicine.api.key}")
    private String apiKey;


    @Value("${medicine.api.url}")
    private String apiUrl;

    //리뷰 리스트 불러오기
    @Override
    public ResponseEntity<? super GetReviewResponseDto> getReview(String ITEM_SEQ) {
//        List<ReviewEntity> resultSet = new ArrayList<>();
        List<GetReviewResultSet> resultSet = new ArrayList<>();
//        List<ImageEntity> imageEntities = new ArrayList<>();

        try {
            String itemSeq = fetchItemSeq(ITEM_SEQ);
            boolean existsByItemSeq = reviewRepository.existsByItemSeq(itemSeq);
            if (!existsByItemSeq) return GetReviewResponseDto.notExistReview();

            // 명시적으로 변수의 유형을 지정하여 할당
//            resultSet.addAll(reviewRepository.getReview(ITEM_SEQ));
            resultSet = reviewRepository.getReview(itemSeq);

            // 이미지 엔터티를 가져오는 코드
//            imageEntities = imageRepository.findByReviewNumber(reviewNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        // 이미지 엔터티와 함께 리뷰 결과를 반환
//        return GetReviewResponseDto.success(resultSet);
//        List<ReviewListItem> reviewListItems = ReviewListItem.copyList(resultSet);

        // 변환된 리스트를 가지고 성공 응답 DTO 생성
        return GetReviewResponseDto.success(resultSet);
    }



    //리뷰 작성하기
    @Override
    public ResponseEntity<? super PostReviewResponseDto> postReview(PostReviewRequestDto dto, String ITEM_SEQ, String userId) {
        try {
            // 외부 API를 호출하여 ITEM_SEQ 값을 가져온다
            String itemSeq = fetchItemSeq(ITEM_SEQ);

            // 가져온 ITEM_SEQ 값을 사용하여 ReviewEntity 객체를 생성한다
            ReviewEntity reviewEntity = new ReviewEntity(dto, itemSeq, userId);

            // ReviewEntity를 저장한다
            reviewRepository.save(reviewEntity);

            // 응답을 생성하여 반환한다
            return ResponseEntity.ok(new PostReviewResponseDto());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PostReviewResponseDto());
        }
    }

    //타임아웃
    private RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(30000);
        clientHttpRequestFactory.setReadTimeout(90000);
        return new RestTemplate(clientHttpRequestFactory);
    }

    //외부 api 불러와서 itemSeq값 가져오기
    private String fetchItemSeq(String ITEM_SEQ) {
        try {
            RestTemplate restTemplate = restTemplate();
            String fullUrl = apiUrl + "?serviceKey=" + apiKey + "&type=json&item_seq=" + ITEM_SEQ + "&img_regist_ts=&pageNo=1&numOfRows=16&edi_code=";
            System.out.println("API 호출 URL: " + fullUrl);

            String responseData = restTemplate.getForObject(fullUrl, String.class);

            // Jackson ObjectMapper 객체 생성
            ObjectMapper objectMapper = new ObjectMapper();

            // 응답 데이터를 JSON 형식으로 파싱하여 JsonNode 객체로 변환
            JsonNode rootNode = objectMapper.readTree(responseData);

            // JSON 데이터에서 필요한 정보를 추출하여 변수에 저장
            String itemSeq = rootNode.path("body").path("items").get(0).path("ITEM_SEQ").asText();
            System.out.println("추출된 itemSeq값: " + itemSeq);

            // 추출한 정보 반환
            return itemSeq;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch ITEM_SEQ from external API");
        }
    }
}
