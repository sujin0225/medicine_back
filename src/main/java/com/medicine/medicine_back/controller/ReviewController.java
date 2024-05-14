package com.medicine.medicine_back.controller;

import com.medicine.medicine_back.dto.request.review.PatchReviewRequestDto;
import com.medicine.medicine_back.dto.request.review.PostReviewRequestDto;
//import com.medicine.medicine_back.dto.response.review.GetReviewResponseDto;
import com.medicine.medicine_back.dto.response.review.*;
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

    //리뷰 리스트 불러오기
    @GetMapping("/{ITEM_SEQ}")
    public ResponseEntity<? super GetReviewListResponseDto> getReviewList(
            @PathVariable("ITEM_SEQ") String ITEM_SEQ
    ){
        ResponseEntity<? super GetReviewListResponseDto> response = reviewService.getReviewList(ITEM_SEQ);
        return response;
    }

    //리뷰 불러오기
    @GetMapping("/details/{reviewNumber}")
    public ResponseEntity<? super GetReviewResponseDto> getReview(
            @PathVariable("reviewNumber") Integer reviewNumber
    ){
        ResponseEntity<? super GetReviewResponseDto> response = reviewService.getReview(reviewNumber);
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

    //리뷰 삭제하기
    @DeleteMapping("/{reviewNumber}")
    public ResponseEntity<?super DeleteReviewResponseDto> deleteReview(
            @PathVariable("reviewNumber") Integer reviewNumber,
            @AuthenticationPrincipal String userId
    ){
        ResponseEntity<? super DeleteReviewResponseDto> response = reviewService.deleteReview(reviewNumber, userId);
        return response;
    }

    //리뷰 수정하기
    @PatchMapping("/{reviewNumber}")
    public ResponseEntity<? super PatchReviewResponseDto> patchReview(
            @RequestBody @Valid PatchReviewRequestDto requestBody,
            @PathVariable("reviewNumber") Integer reviewNumber,
            @AuthenticationPrincipal String userId
            ) {
        ResponseEntity<? super PatchReviewResponseDto> response = reviewService.patchReview(requestBody, reviewNumber, userId);
        return response;
    }

    //도움돼요 리스트 불러오기
    @GetMapping("/helpful-list/{reviewNumber}")
    public ResponseEntity<? super GetHelpfulListResponseDto> getFavoriteList(
            @PathVariable("reviewNumber") Integer reviewNumber
    ){
        ResponseEntity<? super GetHelpfulListResponseDto> response = reviewService.getHelpfulList(reviewNumber);
        return response;
    }

    //도움돼요 기능
    @PutMapping("/helpful/{reviewNumber}")
    public ResponseEntity<? super PutHelpfulResponseDto> putFavorite(
            @PathVariable("reviewNumber") Integer reviewNumber,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PutHelpfulResponseDto> response = reviewService.putFavorite(reviewNumber, userId);
        return response;
    }
}
