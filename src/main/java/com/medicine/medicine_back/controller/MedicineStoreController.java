package com.medicine.medicine_back.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.medicine.medicine_back.dto.response.medicineStore.MedicineStoreResponseDto;
import com.medicine.medicine_back.service.MedicineStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MedicineStoreController {
    private final MedicineStoreService medicineStoreService;

    @GetMapping("/medicine-stores")
    public ResponseEntity<? super MedicineStoreResponseDto> getMedicineStoreAPI() throws JsonProcessingException {
        ResponseEntity<? super MedicineStoreResponseDto> response = medicineStoreService.getMedicineStoreAPI();
        return response;
    }
}
