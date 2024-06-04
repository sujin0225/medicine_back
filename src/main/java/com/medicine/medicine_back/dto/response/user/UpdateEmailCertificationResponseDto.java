package com.medicine.medicine_back.dto.response.user;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.dto.response.auth.EmailCertificationResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UpdateEmailCertificationResponseDto extends ResponseDto {
    private UpdateEmailCertificationResponseDto() {
        super();
    }

    //성공
    public static ResponseEntity<UpdateEmailCertificationResponseDto> success() {
        UpdateEmailCertificationResponseDto responseBody = new UpdateEmailCertificationResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    //메일 전송 실패
    public static ResponseEntity<ResponseDto> mailSendFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.MAIL_FAIL, ResponseMessage.MAIL_FAIL);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
}
