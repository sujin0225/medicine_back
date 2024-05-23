package com.medicine.medicine_back.repository;

import com.medicine.medicine_back.entity.MedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<MedicineEntity, String> {
}
