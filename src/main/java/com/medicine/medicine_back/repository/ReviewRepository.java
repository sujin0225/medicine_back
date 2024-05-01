package com.medicine.medicine_back.repository;

import com.medicine.medicine_back.entity.ReviewEntity;
//import com.medicine.medicine_back.repository.resultSet.GetReviewResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {
//    boolean existsByItemSeq(String ITEM_SEQ);
//    ReviewEntity findByItemSeq(String ITEM_SEQ);

//    @Query(
//            value =
//                    "SELECT " +
//                            "R.ITEM_SEQ AS ITEM_SEQ, " +
//                            "R.review_number AS reviewNumber, " +
//                            "R.star_rating AS starRating, " +
//                            "R.content AS content, " +
//                            "R.write_datetime AS writeDatetime, " +
//                            "R.ITEM_SEQ AS ITEM_SEQ, " +
//                            "R.user_id AS userId " +
//                            "FROM review AS R " +
//                            "INNER JOIN user AS U " +
//                            "ON R.user_id = U.user_id " +
//                            "WHERE R.ITEM_SEQ = ?1 ",
//            nativeQuery = true
//    )
//    List<GetReviewResultSet> getReview(String ITEM_SEQ);
}

