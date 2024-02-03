package com.medicine.medicine_back.service;

import com.medicine.medicine_back.dto.request.auth.EmailCertificationRequestDto;
import com.medicine.medicine_back.dto.request.auth.IdCheckRequestDto;
import com.medicine.medicine_back.dto.response.auth.EmailCertificationResponseDto;
import com.medicine.medicine_back.dto.response.auth.IdCheckResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto);
    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);
}
