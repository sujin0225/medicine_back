package com.medicine.medicine_back.dto.response.medicinePermission;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class MedicinePermissionResponseDto extends ResponseDto {

    public MedicinePermissionResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    //성공
    public static ResponseEntity<MedicinePermissionResponseDto> success() {
        MedicinePermissionResponseDto result = new MedicinePermissionResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
