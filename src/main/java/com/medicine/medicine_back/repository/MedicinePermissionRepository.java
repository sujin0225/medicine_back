package com.medicine.medicine_back.repository;

import com.medicine.medicine_back.entity.MedicinePermissionEntity;
import com.medicine.medicine_back.repository.resultSet.GetMedicinePermissionResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicinePermissionRepository extends JpaRepository<MedicinePermissionEntity, String> {
    @Query(
            value =
                    "SELECT * FROM medicinepermission " +
                    "WHERE ITEM_SEQ = :ITEM_SEQ",
            nativeQuery = true
    )
    GetMedicinePermissionResultSet getMedicinePermission(String ITEM_SEQ);
}
