package com.medicine.medicine_back.service.implement;

import com.medicine.medicine_back.common.CertificationNumber;
import com.medicine.medicine_back.dto.request.auth.CheckCertificationRequestDto;
import com.medicine.medicine_back.dto.request.user.PatchEmailRequestDto;
import com.medicine.medicine_back.dto.request.user.PatchPasswordRequestDto;
import com.medicine.medicine_back.dto.request.user.UpdateEmailCertificationRequestDto;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.dto.response.auth.CheckCertificationResponseDto;
import com.medicine.medicine_back.dto.response.auth.EmailCertificationResponseDto;
import com.medicine.medicine_back.dto.response.auth.SignUpResponseDto;
import com.medicine.medicine_back.dto.response.user.*;
import com.medicine.medicine_back.entity.CertificationEntity;
import com.medicine.medicine_back.entity.UserEntity;
import com.medicine.medicine_back.provider.EmailProvider;
import com.medicine.medicine_back.repository.CertificationRepository;
import com.medicine.medicine_back.repository.UserRepository;
import com.medicine.medicine_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;
    private final EmailProvider emailProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    //비밀번호 수정
    @Override
    public ResponseEntity<? super PatchPasswordResponseDto> patchUserpassword(PatchPasswordRequestDto dto, String userId) {
        try {
            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) PatchPasswordResponseDto.noExistUser();

            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            userEntity.setPassword(encodedPassword);

            userRepository.save(userEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchPasswordResponseDto.success();
    }

    //이메일 변경
    @Override
    public ResponseEntity<? super PatchEmailResponseDto> patchUserEmail(PatchEmailRequestDto dto, String userId) {
        try {
            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) PatchEmailResponseDto.noExistUser();

            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);
            boolean isMatched =
                    certificationEntity.getEmail().equals(email) &&
                            certificationEntity.getCertificationNumber().equals(certificationNumber);
            if (!isMatched) return PatchEmailResponseDto.certificationFail();

            userEntity.setEmail(email);
            userRepository.save(userEntity);

            certificationRepository.deleteByUserId(userId);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchEmailResponseDto.success();
    }

    //이메일 수정시 이메일 인증
    @Override
    public ResponseEntity<? super UpdateEmailCertificationResponseDto> checkCertification(UpdateEmailCertificationRequestDto dto) {
        try {
            String userId = dto.getId();
            String email = dto.getEmail();

            String certificationNumber = CertificationNumber.getCertification();

            boolean isSuccessed = emailProvider.sendCertificationMail(email, certificationNumber);
            if (!isSuccessed) return EmailCertificationResponseDto.mailSendFail();

            CertificationEntity certificationEntity = new CertificationEntity(userId, email, certificationNumber);
            certificationRepository.save(certificationEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return UpdateEmailCertificationResponseDto.success();
    }
}
