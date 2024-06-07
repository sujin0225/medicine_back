package com.medicine.medicine_back.service;

import com.medicine.medicine_back.dto.response.medicinePermission.GetMedicinePermissionResponseDto;
import com.medicine.medicine_back.dto.response.medicinePermission.MedicinePermissionResponseDto;
import org.springframework.http.ResponseEntity;

public interface MedicinePermissionService {
    ResponseEntity<? super MedicinePermissionResponseDto> getMedicinePermissionAPI();
    ResponseEntity<? super GetMedicinePermissionResponseDto> getMedicinePermission(String ITEM_SEQ);
}
