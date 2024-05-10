package com.medicine.medicine_back.service.implement;

import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.dto.response.user.GetSignInUserResponseDto;
import com.medicine.medicine_back.entity.UserEntity;
import com.medicine.medicine_back.repository.UserRepository;
import com.medicine.medicine_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId) {
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return GetSignInUserResponseDto.notExistUser();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetSignInUserResponseDto.success(userEntity);
    }
}
