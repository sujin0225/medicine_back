package com.medicine.medicine_back.controller;

import com.medicine.medicine_back.dto.response.medicine.GetMedicineResponseDto;
import com.medicine.medicine_back.dto.response.medicine.MedicineResponseDto;
import com.medicine.medicine_back.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicine")
@RequiredArgsConstructor
public class MedicineController {
    private final MedicineService medicineService;

    //의약품 정보 api db에 저장
    @GetMapping("/save")
    public ResponseEntity<? super MedicineResponseDto> getMedicineAPI() {
        ResponseEntity<? super MedicineResponseDto> response = medicineService.getMedicineAPI();
        return response;
    }

    //의약품 상세 정보 불러오기
    @GetMapping("/{ITEM_SEQ}")
    public ResponseEntity<? super GetMedicineResponseDto> getMedicine(
            @PathVariable("ITEM_SEQ") String ITEM_SEQ
    ){
        ResponseEntity<? super GetMedicineResponseDto> response = medicineService.getMedicine(ITEM_SEQ);
        return response;
    }
}
