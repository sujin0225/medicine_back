package com.medicine.medicine_back.service.implement;

import com.medicine.medicine_back.common.CertificationNumber;
import com.medicine.medicine_back.dto.request.auth.CheckCertificationRequestDto;
import com.medicine.medicine_back.dto.request.auth.EmailCertificationRequestDto;
import com.medicine.medicine_back.dto.request.auth.IdCheckRequestDto;
import com.medicine.medicine_back.dto.request.auth.SignUpRequestDto;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.dto.response.auth.CheckCertificationResponseDto;
import com.medicine.medicine_back.dto.response.auth.EmailCertificationResponseDto;
import com.medicine.medicine_back.dto.response.auth.IdCheckResponseDto;
import com.medicine.medicine_back.dto.response.auth.SignUpResponseDto;
import com.medicine.medicine_back.entity.CertificationEntity;
import com.medicine.medicine_back.entity.UserEntity;
import com.medicine.medicine_back.provider.EmailProvider;
import com.medicine.medicine_back.provider.JwtProvider;
import com.medicine.medicine_back.repository.CertificationRepository;
import com.medicine.medicine_back.repository.UserRepository;
import com.medicine.medicine_back.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;

    private final EmailProvider emailProvider;
    private final JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //아이디 중복 체크
    @Override
    public ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto) {
        try {

            String userId = dto.getId();
            boolean isExistId = userRepository.existsByUserId(userId);
            if (isExistId) return IdCheckResponseDto.duplicateId();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return IdCheckResponseDto.success();
    }

    //이메일 인증
    @Override
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto) {
        try {
            String userId = dto.getId();
            String email = dto.getEmail();

            boolean isExistId = userRepository.existsByUserId(userId);
            if (isExistId) return EmailCertificationResponseDto.duplicateId();

            String certificationNumber = CertificationNumber.getCertification();

            boolean isSuccessed = emailProvider.sendCertificationMail(email, certificationNumber);
            if (!isSuccessed) return EmailCertificationResponseDto.mailSendFail();

            CertificationEntity certificationEntity = new CertificationEntity(userId, email, certificationNumber);
            certificationRepository.save(certificationEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return EmailCertificationResponseDto.success();
    }

    //인증 번호 확인
    @Override
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto) {
        try {

            String userId = dto.getId();
            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);
            if (certificationEntity == null) return CheckCertificationResponseDto.certificationFail();

            boolean isMatched = certificationEntity.getEmail().equals(email) && certificationEntity.getCertificationNumber().equals(certificationNumber);
            if (!isMatched) return CheckCertificationResponseDto.certificationFail();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return CheckCertificationResponseDto.success();
    }

    //회원 가입
    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
        try {

            String userId = dto.getId();
            boolean isExistId = userRepository.existsByUserId(userId);
            if (isExistId) return SignUpResponseDto.duplicateId();

            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);
            boolean isMatched =
                    certificationEntity.getEmail().equals(email) &&
                            certificationEntity.getCertificationNumber().equals(certificationNumber);
            if (!isMatched) return SignUpResponseDto.certificationFail();

            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);

//            certificationRepository.delete(certificationEntity);
            certificationRepository.deleteByUserId(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignUpResponseDto.success();
    }
}
