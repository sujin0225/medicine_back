package com.medicine.medicine_back.service;

import com.medicine.medicine_back.dto.request.auth.*;
import com.medicine.medicine_back.dto.request.user.PatchEmailRequestDto;
import com.medicine.medicine_back.dto.response.auth.*;
import com.medicine.medicine_back.dto.response.user.PatchEmailResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto);
    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);
    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
