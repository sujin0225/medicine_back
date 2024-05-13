package com.medicine.medicine_back.repository;

import com.medicine.medicine_back.entity.ReviewEntity;
import com.medicine.medicine_back.repository.resultSet.GetReviewResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewGetRepository extends JpaRepository<ReviewEntity, Integer> {
        @Query(
                value =
                        "SELECT " +
                                "R.review_number AS reviewNumber, " +
                                "R.ITEM_SEQ AS itemSeq, " +
                                "R.user_id AS userId, " +
                                "R.content AS content, " +
                                "R.write_datetime AS writeDatetime, " +
                                "R.helpful_count AS helpfulCount, " +
                                "R.star_rating AS starRating " +
                                "FROM review AS R " +
                                "INNER JOIN user AS U " +
                                "ON R.user_id = U.user_id " +
                                "WHERE R.review_Number = :reviewNumber " +
                                "ORDER BY write_datetime DESC",
                nativeQuery = true
        )
        GetReviewResultSet getReview(@Param("reviewNumber") Integer reviewNumber);

//    GetReviewResultSet getReview(Integer reviewNumber);
}
