package com.medicine.medicine_back.dto.response.review;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostReviewResponseDto extends ResponseDto {
    public PostReviewResponseDto() {super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);}

    //성공
    public static ResponseEntity<PostReviewResponseDto> success() {
        PostReviewResponseDto result = new PostReviewResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //존재하지 않는 유저
    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }

    //존재하지 않는 의약품
    public static ResponseEntity<ResponseDto> noExistMedicine() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_MEDICINE, ResponseMessage.NOT_EXISTED_MEDICINE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
