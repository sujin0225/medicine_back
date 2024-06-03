package com.medicine.medicine_back.service.implement;

import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.dto.response.user.DeleteUserResponseDto;
import com.medicine.medicine_back.dto.response.user.GetSignInUserResponseDto;
import com.medicine.medicine_back.entity.UserEntity;
import com.medicine.medicine_back.repository.UserRepository;
import com.medicine.medicine_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;

    //유저
    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId) {
        UserEntity userEntity = null;

        //현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //로그인한 사용자 ID 가져오기
        String currentLoggedInUserId = authentication.getName();
        System.out.println(currentLoggedInUserId);

        // 로그인한 사용자의 ID와 요청받은 userId가 일치하는지 검사
        if (!currentLoggedInUserId.equals(userId)) {
            // ID가 다르면 접근을 거부합니다.
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 권한이 없습니다.");
        }

        try {
            userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return GetSignInUserResponseDto.notExistUser();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetSignInUserResponseDto.success(userEntity);
    }

    //회원 탈퇴
    @Override
    public ResponseEntity<? super DeleteUserResponseDto> deleteUser(String userId) {
        UserEntity userEntity = null;

        //현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //로그인한 사용자 ID 가져오기
        String currentLoggedInUserId = authentication.getName();
        System.out.println(currentLoggedInUserId);

        // 로그인한 사용자의 ID와 요청받은 userId가 일치하는지 검사
        if (!currentLoggedInUserId.equals(userId)) {
            // ID가 다르면 접근을 거부합니다.
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 권한이 없습니다.");
        }

        try {
            userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return DeleteUserResponseDto.noExistUser();

            String UserId = userEntity.getUserId();
            boolean isUserId = UserId.equals(userId);
            if (!isUserId) return DeleteUserResponseDto.noPermission();

            userRepository.delete(userEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return DeleteUserResponseDto.success(userEntity);
    }
}
