package com.medicine.medicine_back.controller;

import com.medicine.medicine_back.dto.response.medicineStore.GetMedicineStoreListResponseDto;
import com.medicine.medicine_back.dto.response.medicineStore.MedicineStoreResponseDto;
import com.medicine.medicine_back.entity.MedicineStoreEntity;
import com.medicine.medicine_back.service.MedicineStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medicine-stores")
@RequiredArgsConstructor
public class MedicineStoreController {
    private final MedicineStoreService medicineStoreService;

    //외부 api db에 저장
    @GetMapping("/save")
    public ResponseEntity<? super MedicineStoreResponseDto> getMedicineStoreAPI() {
        ResponseEntity<? super MedicineStoreResponseDto> response = medicineStoreService.getMedicineStoreAPI();
        return response;
    }

    //사용자 위치 받아오기
    @PostMapping("/locations")
    public ResponseEntity<?> getNearbyLocations(@RequestBody Map<String, Double> coordinates) {
        Double x = coordinates.get("X");
        Double y = coordinates.get("Y");

        try {
            List<MedicineStoreEntity> stores = medicineStoreService.findClosestStores(y, x);
            return ResponseEntity.ok(stores);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 내부 오류가 발생했습니다.");
        }
    }

    //상비의약품 판매처 지역으로 검색
    @GetMapping("/areaSearch")
    public ResponseEntity<? super GetMedicineStoreListResponseDto> findAreaStore(
            @RequestParam String address,
            @RequestParam(defaultValue = "1000") int radius
    ){
        ResponseEntity<? super GetMedicineStoreListResponseDto> response = medicineStoreService.findAreaStore(address, radius);
        return response;
    }
}