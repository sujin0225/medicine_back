package com.medicine.medicine_back.controller;

import com.medicine.medicine_back.dto.response.review.DeleteReviewResponseDto;
import com.medicine.medicine_back.dto.response.user.DeleteUserResponseDto;
import com.medicine.medicine_back.dto.response.user.GetSignInUserResponseDto;
import com.medicine.medicine_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{user_id}")
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super GetSignInUserResponseDto> response = userService.getSignInUser(userId);
        return response;
    }

    //회원 탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<?super DeleteUserResponseDto> deleteReview(
            @AuthenticationPrincipal String userId
    ){
        ResponseEntity<? super DeleteUserResponseDto> response = userService.deleteUser(userId);
        return response;
    }
}
