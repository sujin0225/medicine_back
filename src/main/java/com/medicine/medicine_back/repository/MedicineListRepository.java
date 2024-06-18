package com.medicine.medicine_back.repository;

import com.medicine.medicine_back.entity.MedicineEntity;
import com.medicine.medicine_back.repository.resultSet.GetMedicineResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MedicineListRepository extends JpaRepository<MedicineEntity, String>, PagingAndSortingRepository<MedicineEntity, String> {
    @Query(
            value =
                    "SELECT * FROM medicines ",
            nativeQuery = true
    )
    GetMedicineResultSet getMedicineList(int page, int pageSize);
}
