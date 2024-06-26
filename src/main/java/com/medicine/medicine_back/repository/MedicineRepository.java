package com.medicine.medicine_back.repository;

import com.medicine.medicine_back.entity.MedicineEntity;
import com.medicine.medicine_back.repository.resultSet.GetMedicineResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<MedicineEntity, String> {
    @Query(
            value =
                    "SELECT * FROM medicines WHERE ITEM_SEQ = :ITEM_SEQ",
            nativeQuery = true
    )
    GetMedicineResultSet getMedicine(String ITEM_SEQ);
}
