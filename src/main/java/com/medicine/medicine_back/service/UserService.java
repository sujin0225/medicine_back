package com.medicine.medicine_back.service;

import com.medicine.medicine_back.dto.request.user.PatchPasswordRequestDto;
import com.medicine.medicine_back.dto.response.user.DeleteUserResponseDto;
import com.medicine.medicine_back.dto.response.user.GetSignInUserResponseDto;
import com.medicine.medicine_back.dto.response.user.PatchPasswordResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId);
    ResponseEntity<? super DeleteUserResponseDto> deleteUser(String userId);
    ResponseEntity<? super PatchPasswordResponseDto> patchUserpassword(PatchPasswordRequestDto dto, String userId);
}
