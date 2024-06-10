package com.medicine.medicine_back.controller;

import com.medicine.medicine_back.dto.response.medicinePermission.GetMedicinePermissionResponseDto;
import com.medicine.medicine_back.dto.response.medicinePermission.MedicinePermissionResponseDto;
import com.medicine.medicine_back.service.MedicinePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    //의약품 제품 허가정보 불러오가
    @GetMapping("/{ITEM_SEQ}")
    public ResponseEntity<? super GetMedicinePermissionResponseDto> getMedicinePermission(
            @PathVariable("ITEM_SEQ") String ITEM_SEQ
    ){
        ResponseEntity<? super GetMedicinePermissionResponseDto> response = medicinePermissionService.getMedicinePermission(ITEM_SEQ);
        return response;
    }
}
