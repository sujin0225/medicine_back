package com.medicine.medicine_back.dto.response.user;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PatchPasswordResponseDto extends ResponseDto {
    private PatchPasswordResponseDto() {
        super();
    }

    //성공
    public static ResponseEntity<PatchPasswordResponseDto> success() {
        PatchPasswordResponseDto responseBody = new PatchPasswordResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    //존재하지 않는 유저
    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
