package com.medicine.medicine_back.service;

import com.medicine.medicine_back.dto.request.auth.CheckCertificationRequestDto;
import com.medicine.medicine_back.dto.request.user.PatchEmailRequestDto;
import com.medicine.medicine_back.dto.request.user.PatchPasswordRequestDto;
import com.medicine.medicine_back.dto.request.user.UpdateEmailCertificationRequestDto;
import com.medicine.medicine_back.dto.response.auth.CheckCertificationResponseDto;
import com.medicine.medicine_back.dto.response.user.*;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId);
    ResponseEntity<? super DeleteUserResponseDto> deleteUser(String userId);
    ResponseEntity<? super PatchPasswordResponseDto> patchUserpassword(PatchPasswordRequestDto dto, String userId);
    ResponseEntity<? super PatchEmailResponseDto> patchUserEmail(PatchEmailRequestDto dto, String userId);
    ResponseEntity<? super UpdateEmailCertificationResponseDto> checkCertification(UpdateEmailCertificationRequestDto dto);
}
