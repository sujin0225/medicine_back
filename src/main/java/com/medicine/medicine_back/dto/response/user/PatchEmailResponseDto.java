package com.medicine.medicine_back.dto.response.user;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PatchEmailResponseDto extends ResponseDto {
    private PatchEmailResponseDto() {
        super();
    }

    //성공
    public static ResponseEntity<PatchEmailResponseDto> success() {
        PatchEmailResponseDto responseBody = new PatchEmailResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    //존재하지 않는 유저
    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    //이메일 인증번호 불일치
    public static ResponseEntity<ResponseDto> certificationFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.CERTIFICATION_FAIL, ResponseMessage.CERTIFICATION_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}