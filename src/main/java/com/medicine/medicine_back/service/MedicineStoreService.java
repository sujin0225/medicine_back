package com.medicine.medicine_back.service;
import com.medicine.medicine_back.dto.response.medicineStore.MedicineStoreResponseDto;
import com.medicine.medicine_back.entity.MedicineStoreEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MedicineStoreService {
    ResponseEntity<? super MedicineStoreResponseDto> getMedicineStoreAPI();
    List<MedicineStoreEntity> findClosestStores(double Y, double X);
}
