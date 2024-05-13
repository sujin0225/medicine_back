package com.medicine.medicine_back.service.implement;
import com.medicine.medicine_back.dto.request.review.PatchReviewRequestDto;
import com.medicine.medicine_back.dto.request.review.PostReviewRequestDto;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.dto.response.review.*;
import com.medicine.medicine_back.entity.ImageEntity;
import com.medicine.medicine_back.entity.ReviewEntity;
import com.medicine.medicine_back.repository.ImageRepository;
import com.medicine.medicine_back.repository.ReviewGetRepository;
import com.medicine.medicine_back.repository.ReviewRepository;
import com.medicine.medicine_back.repository.UserRepository;
import com.medicine.medicine_back.repository.resultSet.GetReviewResultSet;
import com.medicine.medicine_back.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImplement implements ReviewService {
    private final UserRepository userRespository;
    private final ReviewRepository reviewRepository;
    private final ReviewGetRepository reviewGetRepository;
    private final ImageRepository imageRepository;
    private final RestTemplate restTemplate;

    @Value("${medicine.api.key}")
    private String apiKey;


    @Value("${medicine.api.url}")
    private String apiUrl;

    //리뷰 리스트 불러오기
    @Override
    public ResponseEntity<? super GetReviewListResponseDto> getReviewList(String ITEM_SEQ) {
        List<GetReviewResultSet> resultSet = new ArrayList<>();
        List<ImageEntity> imageEntities = new ArrayList<>();

        try {
            boolean existsByItemSeq = reviewRepository.existsByItemSeq(ITEM_SEQ);
            if (!existsByItemSeq) return GetReviewListResponseDto.notExistReview();

            resultSet = reviewRepository.getReviewList(ITEM_SEQ);
            if (resultSet == null || resultSet.isEmpty()) return GetReviewListResponseDto.notExistReview();

            // resultSet에서 reviewNumber를 추출하여 리스트 생성
            List<Integer> reviewNumber = resultSet.stream()
                    .map(GetReviewResultSet::getReviewNumber)
                    .collect(Collectors.toList());

            // 이미지 엔터티를 가져오는 코드
            if (!reviewNumber.isEmpty()) {
                imageEntities = imageRepository.findByReviewNumberIn(reviewNumber);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        // 변환된 리스트를 가지고 성공 응답 DTO 생성
        return GetReviewListResponseDto.success(resultSet, imageEntities);
    }

    @Override
    public ResponseEntity<? super GetReviewResponseDto> getReview(Integer reviewNumber) {
        GetReviewResultSet resultSet = null;
        List<ImageEntity> imageEntities = new ArrayList<>();

        try {
            resultSet = reviewGetRepository.getReview(reviewNumber);
            if (resultSet == null) return GetReviewResponseDto.notExistReview();

            imageEntities = imageRepository.findByReviewNumber(reviewNumber);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetReviewResponseDto.success(resultSet, imageEntities);
    }

    //리뷰 작성하기
    @Override
    public ResponseEntity<? super PostReviewResponseDto> postReview(PostReviewRequestDto dto, String ITEM_SEQ, String userId) {
        try {
            // 외부 API를 호출하여 ITEM_SEQ 값을 가져온다
//            String itemSeq = fetchItemSeq(ITEM_SEQ);

            boolean existedEmail = userRespository.existsByUserId(userId);
            if(!existedEmail) return PostReviewResponseDto.noExistUser();

            // 가져온 ITEM_SEQ 값을 사용하여 ReviewEntity 객체를 생성한다
            ReviewEntity reviewEntity = new ReviewEntity(dto, ITEM_SEQ, userId);

            // ReviewEntity를 저장한다
            reviewRepository.save(reviewEntity);

            int reviewNumber = reviewEntity.getReviewNumber();
            String itemSeq = reviewEntity.getItemSeq();
            List<String> reviewImageList = dto.getReviewImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for (String image: reviewImageList) {
                ImageEntity imageEntity = new ImageEntity(reviewNumber, image);
                imageEntities.add(imageEntity);
            }
            imageRepository.saveAll(imageEntities);
            // 응답을 생성하여 반환한다
//            return ResponseEntity.ok(new PostReviewResponseDto());
        } catch (Exception exception) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PostReviewResponseDto());
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostReviewResponseDto.success();
    }

    //리뷰 삭제
    @Override
    public ResponseEntity<? super DeleteReviewResponseDto> deleteReview(Integer reviewNumber, String userId) {
        try {
            boolean existedUser = userRespository.existsByUserId(userId);
            if (!existedUser) return DeleteReviewResponseDto.noExistUser();

            ReviewEntity reviewEntity = reviewRepository.findByReviewNumber(reviewNumber);
            if (reviewEntity == null) return DeleteReviewResponseDto.noExistReview();

            String writerId = reviewEntity.getUserId();
            boolean isWriter = writerId.equals(userId);
            if (!isWriter) return DeleteReviewResponseDto.noPermission();

            imageRepository.deleteByReviewNumber(reviewNumber);
            reviewRepository.delete(reviewEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return DeleteReviewResponseDto.success();
    }

    //리뷰 수정
    @Override
    public ResponseEntity<? super PatchReviewResponseDto> patchReview(PatchReviewRequestDto dto, Integer reviewNumber, String userId) {
        try {
            ReviewEntity reviewEntity = reviewRepository.findByReviewNumber(reviewNumber);
            if(reviewEntity == null) return PatchReviewResponseDto.noExistMedicine();

            boolean existedUser = userRespository.existsByUserId(userId);
            if(!existedUser) return PatchReviewResponseDto.noExistUser();

            String writerId = reviewEntity.getUserId();
            boolean isWriter = writerId.equals(userId);
            if(!isWriter) return PatchReviewResponseDto.noPermission();

            reviewEntity.patchReview(dto);
            reviewRepository.save(reviewEntity);

            imageRepository.deleteByReviewNumber(reviewNumber);
            List<String> reviewImageList = dto.getReviewImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for(String image: reviewImageList) {
                ImageEntity imageEntity = new ImageEntity(reviewNumber, image);
                imageEntities.add(imageEntity);
            }

            imageRepository.saveAll(imageEntities);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchReviewResponseDto.success();
    }

    //타임아웃
//    private RestTemplate restTemplate() {
//        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
//        clientHttpRequestFactory.setConnectTimeout(30000);
//        clientHttpRequestFactory.setReadTimeout(90000);
//        return new RestTemplate(clientHttpRequestFactory);
//    }

    //외부 api 불러와서 itemSeq값 가져오기
//    private String fetchItemSeq(String ITEM_SEQ) {
//        try {
//            RestTemplate restTemplate = restTemplate();
//            String fullUrl = apiUrl + "?serviceKey=" + apiKey + "&type=json&item_seq=" + ITEM_SEQ + "&img_regist_ts=&pageNo=1&numOfRows=16&edi_code=";
//            System.out.println("API 호출 URL: " + fullUrl);
//
//            String responseData = restTemplate.getForObject(fullUrl, String.class);
//
//            // Jackson ObjectMapper 객체 생성
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            // 응답 데이터를 JSON 형식으로 파싱하여 JsonNode 객체로 변환
//            JsonNode rootNode = objectMapper.readTree(responseData);
//
//            // JSON 데이터에서 필요한 정보를 추출하여 변수에 저장
//            String itemSeq = rootNode.path("body").path("items").get(0).path("ITEM_SEQ").asText();
//            System.out.println("추출된 itemSeq값: " + itemSeq);
//
//            // 추출한 정보 반환
//            return itemSeq;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to fetch ITEM_SEQ from external API");
//        }
//    }
}
