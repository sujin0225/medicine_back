package com.medicine.medicine_back.dto.response.medicine;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class MedicineResponseDto extends ResponseDto {

    public MedicineResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    //성공
    public static ResponseEntity<MedicineResponseDto> success() {
        MedicineResponseDto result = new MedicineResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
