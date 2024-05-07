package com.medicine.medicine_back.repository;

import com.medicine.medicine_back.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
List<ImageEntity> findByReviewNumberIn(List<Integer> reviewNumbers);

}
