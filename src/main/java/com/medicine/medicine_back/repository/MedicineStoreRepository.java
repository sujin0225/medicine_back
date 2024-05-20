package com.medicine.medicine_back.repository;

import com.medicine.medicine_back.entity.MedicineStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineStoreRepository extends JpaRepository<MedicineStoreEntity, String> {

}

