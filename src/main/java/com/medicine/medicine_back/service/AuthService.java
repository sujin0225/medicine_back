package com.medicine.medicine_back.service;

import com.medicine.medicine_back.dto.request.auth.CheckCertificationRequestDto;
import com.medicine.medicine_back.dto.request.auth.EmailCertificationRequestDto;
import com.medicine.medicine_back.dto.request.auth.IdCheckRequestDto;
import com.medicine.medicine_back.dto.request.auth.SignUpRequestDto;
import com.medicine.medicine_back.dto.response.auth.CheckCertificationResponseDto;
import com.medicine.medicine_back.dto.response.auth.EmailCertificationResponseDto;
import com.medicine.medicine_back.dto.response.auth.IdCheckResponseDto;
import com.medicine.medicine_back.dto.response.auth.SignUpResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto);
    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);
    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
}
