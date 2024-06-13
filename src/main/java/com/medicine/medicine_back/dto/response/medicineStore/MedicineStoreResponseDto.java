package com.medicine.medicine_back.dto.response.medicineStore;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MedicineStoreResponseDto extends ResponseDto {
    public MedicineStoreResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }
    //성공
    public static ResponseEntity<MedicineStoreResponseDto> success() {
        MedicineStoreResponseDto result = new MedicineStoreResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
