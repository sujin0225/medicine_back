package com.medicine.medicine_back.service;

import com.medicine.medicine_back.dto.request.review.PatchReviewRequestDto;
import com.medicine.medicine_back.dto.request.review.PostReviewRequestDto;
import com.medicine.medicine_back.dto.request.review.PutFavoriteRequestDto;
import com.medicine.medicine_back.dto.response.review.*;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    ResponseEntity<? super GetReviewListResponseDto> getReviewList(String ITEM_SEQ);
    ResponseEntity<? super GetReviewResponseDto> getReview(Integer reviewNumber);
    ResponseEntity<? super GetHelpfulListResponseDto> getHelpfulList(Integer reviewNumber);
    ResponseEntity<? super PostReviewResponseDto> postReview(PostReviewRequestDto dto, String ITEM_SEQ, String userId);
    ResponseEntity<? super DeleteReviewResponseDto> deleteReview(Integer reviewNumber, String userId);
    ResponseEntity<? super PatchReviewResponseDto> patchReview(PatchReviewRequestDto dto, Integer reviewNumber, String userId);
    ResponseEntity<? super PutHelpfulResponseDto> putHelpful(Integer reviewNumber, String userId);
    ResponseEntity<? super GetFavoriteResponseDto> getFavorite(String userId);
    ResponseEntity<? super PutFavoriteResponseDto> putFavorite(PutFavoriteRequestDto dto, String itemSeq, String userId);
}
