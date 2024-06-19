package com.medicine.medicine_back.repository;

import com.medicine.medicine_back.entity.MedicineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface MedicineListRepository extends JpaRepository<MedicineEntity, String>, PagingAndSortingRepository<MedicineEntity, String> {
@Query(value = "SELECT * FROM medicines WHERE (:item_name IS NULL OR ITEM_NAME LIKE %:item_name%)", nativeQuery = true)
Page<MedicineEntity> findByITEM_NAMEContaining(@Param("item_name") String item_name, Pageable pageable);
}