package com.medicine.medicine_back.service;

import com.medicine.medicine_back.dto.response.medicine.GetMedicineListResponseDto;
import com.medicine.medicine_back.dto.response.medicine.GetMedicineResponseDto;
import com.medicine.medicine_back.dto.response.medicine.MedicineResponseDto;
import org.springframework.http.ResponseEntity;

public interface MedicineService {
    ResponseEntity<? super MedicineResponseDto> getMedicineAPI();
    ResponseEntity<? super GetMedicineResponseDto> getMedicine(String ITEM_SEQ);
    ResponseEntity<? super GetMedicineListResponseDto> getMedicineList(int page, int pageSize, String item_name);
}
