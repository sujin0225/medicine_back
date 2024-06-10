package com.medicine.medicine_back.controller;

import com.medicine.medicine_back.dto.response.medicineInfo.GetMedicineInfoResponseDto;
import com.medicine.medicine_back.dto.response.medicineInfo.MedicineInfoResponseDto;
import com.medicine.medicine_back.service.MedicineInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicineInfo")
@RequiredArgsConstructor
public class MedicineInfoController {
    private final MedicineInfoService medicineInfoService;

    //의약품 복약 정보 api db에 저장
    @GetMapping("/save")
    public ResponseEntity<? super MedicineInfoResponseDto> getMedicineInfoAPI() {
        ResponseEntity<? super MedicineInfoResponseDto> response = medicineInfoService.getMedicineInfoAPI();
        return response;
    }

    //의약품 복약 정보 불러오기
    @GetMapping("/{ITEM_SEQ}")
    public ResponseEntity<? super GetMedicineInfoResponseDto> getMedicineInfo(
            @PathVariable("ITEM_SEQ") String ITEM_SEQ
    ){
        ResponseEntity<? super GetMedicineInfoResponseDto> response = medicineInfoService.getMedicineInfo(ITEM_SEQ);
        return response;
    }
}
