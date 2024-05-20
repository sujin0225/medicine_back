package com.medicine.medicine_back.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.medicine.medicine_back.dto.response.medicineStore.MedicineStoreResponseDto;
import org.springframework.http.ResponseEntity;

public interface MedicineStoreService {
    ResponseEntity<? super MedicineStoreResponseDto> getMedicineStoreAPI() throws JsonProcessingException;
}
