package com.medicine.medicine_back.service.implement;

import com.medicine.medicine_back.common.CertificationNumber;
import com.medicine.medicine_back.dto.request.auth.EmailCertificationRequestDto;
import com.medicine.medicine_back.dto.request.auth.IdCheckRequestDto;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.dto.response.auth.EmailCertificationResponseDto;
import com.medicine.medicine_back.dto.response.auth.IdCheckResponseDto;
import com.medicine.medicine_back.entity.CertificationEntity;
import com.medicine.medicine_back.provider.EmailProvider;
import com.medicine.medicine_back.repository.CertificationRepository;
import com.medicine.medicine_back.repository.UserRepository;
import com.medicine.medicine_back.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;

    private final EmailProvider emailProvider;

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
}
