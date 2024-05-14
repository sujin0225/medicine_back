package com.medicine.medicine_back.repository;

import com.medicine.medicine_back.entity.HelpfulEntity;
import com.medicine.medicine_back.entity.primaryKey.HelpfulPK;
import com.medicine.medicine_back.repository.resultSet.GetHelpfulListResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HelpfulRepository extends JpaRepository<HelpfulEntity, HelpfulPK> {
    HelpfulEntity findByReviewNumberAndUserId(Integer reviewNumber, String userId);

    @Query(
            value =
                    "SELECT " +
                            "U.user_id AS userId " +
                            "FROM helpful AS F " +
                            "INNER JOIN user AS U " +
                            "ON F.user_id = U.user_id " +
                            "WHERE F.review_number = :reviewNumber",
            nativeQuery = true
    )
    List<GetHelpfulListResultSet> getHelpfulList(Integer reviewNumber);

    @Transactional
    void deleteByReviewNumber(Integer reviewNumber);
}
