package com.medicine.medicine_back.repository;

import com.medicine.medicine_back.entity.ReviewEntity;
import com.medicine.medicine_back.repository.resultSet.GetReviewResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewUserRepository extends JpaRepository<ReviewEntity, String> {
//    List<GetReviewResultSet> findByUserId(String UserId);
    List<GetReviewResultSet> findByUserIdOrderByWriteDatetimeDesc(String UserId);
}
