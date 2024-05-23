package com.medicine.medicine_back.service;

import com.medicine.medicine_back.dto.response.medicine.MedicineResponseDto;
import org.springframework.http.ResponseEntity;

public interface MedicineService {
    ResponseEntity<? super MedicineResponseDto> getMedicineAPI();
}
