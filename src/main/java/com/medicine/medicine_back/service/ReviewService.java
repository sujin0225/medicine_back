package com.medicine.medicine_back.service;

import com.medicine.medicine_back.dto.request.review.PostReviewRequestDto;
//import com.medicine.medicine_back.dto.response.review.GetReviewResponseDto;
import com.medicine.medicine_back.dto.response.review.GetReviewResponseDto;
import com.medicine.medicine_back.dto.response.review.PostReviewResponseDto;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    ResponseEntity<? super GetReviewResponseDto> getReview(String ITEM_SEQ);
    ResponseEntity<? super PostReviewResponseDto> postReview(PostReviewRequestDto dto, String ITEM_SEQ, String userId);
}
