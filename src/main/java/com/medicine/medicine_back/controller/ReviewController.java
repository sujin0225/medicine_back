package com.medicine.medicine_back.controller;

import com.medicine.medicine_back.dto.request.review.PostReviewRequestDto;
//import com.medicine.medicine_back.dto.response.review.GetReviewResponseDto;
import com.medicine.medicine_back.dto.response.review.GetReviewResponseDto;
import com.medicine.medicine_back.dto.response.review.PostReviewResponseDto;
import com.medicine.medicine_back.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;


//    //리뷰 리스트 불러오기
    @GetMapping("/{ITEM_SEQ}")
    public ResponseEntity<? super GetReviewResponseDto> getReview(
            @PathVariable("ITEM_SEQ") String ITEM_SEQ
    ){
        ResponseEntity<? super GetReviewResponseDto> response = reviewService.getReview(ITEM_SEQ);
        return response;
    }

    //리뷰 작성하기
    @PostMapping("/{ITEM_SEQ}")
    public ResponseEntity<? super PostReviewResponseDto> postReview(
            @RequestBody @Valid PostReviewRequestDto requestBody,
            @PathVariable("ITEM_SEQ") String ITEM_SEQ,
            @AuthenticationPrincipal String userId
    ){
        ResponseEntity<? super PostReviewResponseDto> response = reviewService.postReview(requestBody, ITEM_SEQ, userId);
        return response;
    }

}
