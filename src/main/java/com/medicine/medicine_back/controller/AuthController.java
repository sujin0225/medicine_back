package com.medicine.medicine_back.controller;

import com.medicine.medicine_back.dto.request.auth.CheckCertificationRequestDto;
import com.medicine.medicine_back.dto.request.auth.EmailCertificationRequestDto;
import com.medicine.medicine_back.dto.request.auth.IdCheckRequestDto;
import com.medicine.medicine_back.dto.request.auth.SignUpRequestDto;
import com.medicine.medicine_back.dto.response.auth.CheckCertificationResponseDto;
import com.medicine.medicine_back.dto.response.auth.EmailCertificationResponseDto;
import com.medicine.medicine_back.dto.response.auth.IdCheckResponseDto;
import com.medicine.medicine_back.dto.response.auth.SignUpResponseDto;
import com.medicine.medicine_back.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //아이디 중복 확인
    @PostMapping("/id-check")
    public ResponseEntity<? super IdCheckResponseDto> idCheck(
            @RequestBody @Valid IdCheckRequestDto requestBody
            ) {
            ResponseEntity<? super IdCheckResponseDto> response = authService.idCheck(requestBody);
            return response;
    }

    //이메일 인증
    @PostMapping("/email-certification")
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification (
            @RequestBody @Valid EmailCertificationRequestDto requestBody
    ) {
        ResponseEntity<? super EmailCertificationResponseDto> response = authService.emailCertification(requestBody);
        return response;
    }

    //인증 번호 확인
    @PostMapping("/check-certification")
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification (
            @RequestBody @Valid CheckCertificationRequestDto requestBody
    ) {
        ResponseEntity<? super CheckCertificationResponseDto> response = authService.checkCertification(requestBody);
        return response;
    }

    //회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp (
            @RequestBody @Valid SignUpRequestDto requestBody
    ){
        ResponseEntity<? super SignUpResponseDto> response = authService.signUp(requestBody);
        return response;
    }
}
