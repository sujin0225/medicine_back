package com.medicine.medicine_back.repository;

import com.medicine.medicine_back.entity.MedicineInfoEntity;
import com.medicine.medicine_back.repository.resultSet.GetMedicineInfoResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineInfoRepository extends JpaRepository<MedicineInfoEntity, String> {
    @Query(
            value =
            "SELECT * FROM medicine_info " +
            "WHERE ITEM_SEQ = :ITEM_SEQ",
            nativeQuery = true
    )
    GetMedicineInfoResultSet getMedicineInfo(String ITEM_SEQ);
}
