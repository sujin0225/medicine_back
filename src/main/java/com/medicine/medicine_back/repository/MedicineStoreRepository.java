package com.medicine.medicine_back.repository;

import com.medicine.medicine_back.entity.MedicineStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineStoreRepository extends JpaRepository<MedicineStoreEntity, String> {
    @Query(
            value =
                    "SELECT *, (6371 * acos(cos(radians(:latitude)) " +
                            "* cos(radians(Y)) * cos(radians(X) - radians(:longitude)) + sin(radians(:latitude)) " +
                            "* sin(radians(Y)))) AS distance FROM medicine_store ORDER BY distance LIMIT 5",
            nativeQuery = true)
    List<MedicineStoreEntity> findClosest(@Param("latitude") double Y, @Param("longitude") double X);
}