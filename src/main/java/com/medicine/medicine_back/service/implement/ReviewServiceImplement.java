package com.medicine.medicine_back.service.implement;
import com.medicine.medicine_back.dto.request.review.PatchReviewRequestDto;
import com.medicine.medicine_back.dto.request.review.PostReviewRequestDto;
import com.medicine.medicine_back.dto.request.review.PutFavoriteRequestDto;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.dto.response.review.*;
import com.medicine.medicine_back.entity.FavoriteEntity;
import com.medicine.medicine_back.entity.HelpfulEntity;
import com.medicine.medicine_back.entity.ImageEntity;
import com.medicine.medicine_back.entity.ReviewEntity;
import com.medicine.medicine_back.repository.*;
import com.medicine.medicine_back.repository.resultSet.GetFavoriteResultSet;
import com.medicine.medicine_back.repository.resultSet.GetHelpfulListResultSet;
import com.medicine.medicine_back.repository.resultSet.GetReviewResultSet;
import com.medicine.medicine_back.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
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
    private final HelpfulRepository helpfulRepository;
    private final ImageRepository imageRepository;
    private final FavoriteRepository favoriteRepository;
    private final ReviewUserRepository reviewUserRepository;
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

    //리뷰 불러오기
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

    //도움돼요 리스트 불러오기
    @Override
    public ResponseEntity<? super GetHelpfulListResponseDto> getHelpfulList(Integer reviewNumber) {

        List<GetHelpfulListResultSet> resultSets = new ArrayList<>();

        try {
            boolean existedReview = reviewRepository.existsByReviewNumber(reviewNumber);
            if (!existedReview) return GetHelpfulListResponseDto.noExistReview();

            resultSets = helpfulRepository.getHelpfulList(reviewNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetHelpfulListResponseDto.success(resultSets);
    }

    //리뷰 작성하기
    @Override
    public ResponseEntity<? super PostReviewResponseDto> postReview(PostReviewRequestDto dto, String ITEM_SEQ, String userId) {
        try {

            boolean existedEmail = userRespository.existsByUserId(userId);
            if(!existedEmail) return PostReviewResponseDto.noExistUser();

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
        } catch (Exception exception) {
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

    //도움돼요 기능
    @Override
    public ResponseEntity<? super PutHelpfulResponseDto> putHelpful(Integer reviewNumber, String userId) {
        try {
            boolean existedUser = userRespository.existsByUserId(userId);
            if (!existedUser) return PutHelpfulResponseDto.noExistUser();

            ReviewEntity reviewEntity = reviewRepository.findByReviewNumber(reviewNumber);
            if (reviewEntity == null) return PutHelpfulResponseDto.noExistReview();

            HelpfulEntity helpfulEntity = helpfulRepository.findByReviewNumberAndUserId(reviewNumber, userId);
            if (helpfulEntity == null) {
                helpfulEntity = new HelpfulEntity(userId, reviewNumber);
                helpfulRepository.save(helpfulEntity);
                reviewEntity.increaseFavoriteCount();
            }
            else {
                helpfulRepository.delete(helpfulEntity);
                reviewEntity.decreaseFavoriteCount();
            }
            reviewRepository.save(reviewEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PutHelpfulResponseDto.success();
    }

    //관심 의약품 리스트
    @Override
    public ResponseEntity<? super GetFavoriteResponseDto> getFavorite(String userId) {
        List<GetFavoriteResultSet> resultSets = new ArrayList<>();

        try {
            resultSets = favoriteRepository.getFavorite(userId);
            System.out.println(resultSets);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetFavoriteResponseDto.success(resultSets);
    }

    //관심 의약품 저장
    @Override
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(PutFavoriteRequestDto dto, String itemSeq, String userId) {
        try {
            boolean existedUser = userRespository.existsByUserId(userId);
            if (!existedUser) return PutFavoriteResponseDto.noExistUser();

            FavoriteEntity favoriteEntity = favoriteRepository.findByItemSeqAndUserId(itemSeq, userId);
            if (favoriteEntity == null) {
                favoriteEntity = new FavoriteEntity(dto, itemSeq, userId);
                favoriteRepository.save(favoriteEntity);
            } else {
                favoriteRepository.delete(favoriteEntity);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PutFavoriteResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetUserReviewListResponseDto> getUserReviewList(String UserId) {
        List<GetReviewResultSet> resultSet = new ArrayList<>();
        List<ImageEntity> imageEntities = new ArrayList<>();

        try {
            boolean existedUser = userRespository.existsByUserId(UserId);
            if (!existedUser) return GetUserReviewListResponseDto.notExistReview();

            resultSet = reviewUserRepository.findByUserId(UserId);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetUserReviewListResponseDto.success(resultSet, imageEntities);
    }
}
