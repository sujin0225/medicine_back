package com.medicine.medicine_back.service;

import com.medicine.medicine_back.dto.response.medicineInfo.MedicineInfoResponseDto;
import org.springframework.http.ResponseEntity;

public interface MedicineInfoService {
    ResponseEntity<? super MedicineInfoResponseDto> getMedicineInfoAPI();
//    ResponseEntity<? super >
}
