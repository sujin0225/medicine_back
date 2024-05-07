package com.medicine.medicine_back.repository;

import com.medicine.medicine_back.dto.object.ReviewListItem;
import com.medicine.medicine_back.entity.ReviewEntity;
//import com.medicine.medicine_back.repository.resultSet.GetReviewResultSet;
import com.medicine.medicine_back.repository.resultSet.GetReviewResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {
    boolean existsByItemSeq(String ITEM_SEQ);
//    ReviewEntity findByItemSeq(String ITEM_SEQ);
//    List<ReviewEntity> findByItemSeq(String ITEM_SEQ);

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
                            "WHERE R.ITEM_SEQ = :itemSeq " +
                            "ORDER BY write_datetime DESC",
            nativeQuery = true
    )
//    List<ReviewEntity> getReview(@Param("itemSeq") String itemSeq);
    List<GetReviewResultSet> getReview(@Param("itemSeq") String itemSeq);
}

