package com.medicine.medicine_back.repository;

import com.medicine.medicine_back.entity.FavoriteEntity;
import com.medicine.medicine_back.entity.primaryKey.FavoritePK;
import com.medicine.medicine_back.repository.resultSet.GetFavoriteResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, FavoritePK> {
    FavoriteEntity findByItemSeqAndUserId(String itemSeq, String userId);
    @Query(
            value =
                    "SELECT U.user_id AS userId, F.ITEM_SEQ AS itemSeq, F.ITEM_NAME, F.FORM_CODE_NAME, F.CLASS_NO, F.CLASS_NAME, " +
                            "F.ENTP_NAME, F.ETC_OTC_NAME, F.ITEM_ENG_NAME, " +
                            "F.write_datetime, F.ITEM_IMAGE " +
                            "FROM favorite F " +
                            "INNER JOIN user U ON F.user_id = U.user_id " +
                            "WHERE U.user_id = :user_id " +
                            "ORDER BY write_datetime DESC ",
            nativeQuery = true
    )
    List<GetFavoriteResultSet> getFavorite(@Param("user_id") String userId);
}