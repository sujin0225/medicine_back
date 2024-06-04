package com.medicine.medicine_back.controller;

import com.medicine.medicine_back.dto.request.review.PatchReviewRequestDto;
import com.medicine.medicine_back.dto.request.user.PatchEmailRequestDto;
import com.medicine.medicine_back.dto.request.user.PatchPasswordRequestDto;
import com.medicine.medicine_back.dto.request.user.UpdateEmailCertificationRequestDto;
import com.medicine.medicine_back.dto.response.review.PatchReviewResponseDto;
import com.medicine.medicine_back.dto.response.user.*;
import com.medicine.medicine_back.service.UserService;
import jakarta.validation.Valid;
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

    //비밀번호 변경
    @PatchMapping("/patchpassword")
    public ResponseEntity<? super PatchPasswordResponseDto> patchUserpassword(
            @RequestBody @Valid PatchPasswordRequestDto requestBody,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PatchPasswordResponseDto> response = userService.patchUserpassword(requestBody, userId);
        return response;
    }

    //이메일 변경
    @PatchMapping("/patchEmail")
    public ResponseEntity<? super PatchEmailResponseDto> patchUserEmail(
            @RequestBody PatchEmailRequestDto requestBody,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PatchEmailResponseDto> response = userService.patchUserEmail(requestBody, userId);
        return response;
    }

    //이메일 수정시 이메일 인증
    @PostMapping("/update-check-certification")
    public ResponseEntity<? super UpdateEmailCertificationResponseDto> checkCertification (
            @RequestBody @Valid UpdateEmailCertificationRequestDto requestBody
            ){
        ResponseEntity<? super UpdateEmailCertificationResponseDto> response = userService.checkCertification(requestBody);
        return response;
    }
}
