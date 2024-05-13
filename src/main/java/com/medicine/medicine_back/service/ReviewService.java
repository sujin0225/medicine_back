package com.medicine.medicine_back.service;

import com.medicine.medicine_back.dto.request.review.PatchReviewRequestDto;
import com.medicine.medicine_back.dto.request.review.PostReviewRequestDto;
import com.medicine.medicine_back.dto.response.review.*;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    //리뷰 리스트 불러오기
    ResponseEntity<? super GetReviewListResponseDto> getReviewList(String ITEM_SEQ);

    //    ResponseEntity<? super GetReviewListResponseDto> getReviewList(String ITEM_SEQ);
    ResponseEntity<? super GetReviewResponseDto> getReview(Integer reviewNumber);
    ResponseEntity<? super PostReviewResponseDto> postReview(PostReviewRequestDto dto, String ITEM_SEQ, String userId);
    ResponseEntity<? super DeleteReviewResponseDto> deleteReview(Integer reviewNumber, String userId);
    ResponseEntity<? super PatchReviewResponseDto> patchReview(PatchReviewRequestDto dto, Integer reviewNumber, String userId);
}
