package com.medicine.medicine_back.dto.response.medicineInfo;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MedicineInfoResponseDto extends ResponseDto {
    public MedicineInfoResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    //성공
    public static ResponseEntity<MedicineInfoResponseDto> success() {
        MedicineInfoResponseDto result = new MedicineInfoResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
