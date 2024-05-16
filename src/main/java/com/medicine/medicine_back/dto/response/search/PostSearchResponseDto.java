package com.medicine.medicine_back.dto.response.search;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostSearchResponseDto extends ResponseDto{
    public PostSearchResponseDto() {super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);}

    //성공
    public static ResponseEntity<PostSearchResponseDto> success() {
        PostSearchResponseDto result = new PostSearchResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
