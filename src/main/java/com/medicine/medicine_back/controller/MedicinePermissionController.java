package com.medicine.medicine_back.controller;

import com.medicine.medicine_back.dto.response.medicinePermission.MedicinePermissionResponseDto;
import com.medicine.medicine_back.service.MedicinePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicinePermission")
@RequiredArgsConstructor
public class MedicinePermissionController {
    private final MedicinePermissionService medicinePermissionService;

    //의약품 제품 허가정보 api db에 저장
    @GetMapping("/save")
    public ResponseEntity<? super MedicinePermissionResponseDto> getMedicinePermissionAPI() {
        ResponseEntity<? super MedicinePermissionResponseDto> response = medicinePermissionService.getMedicinePermissionAPI();
        return response;
    }
}
