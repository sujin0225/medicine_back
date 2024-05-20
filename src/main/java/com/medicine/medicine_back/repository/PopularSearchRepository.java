package com.medicine.medicine_back.repository;

import com.medicine.medicine_back.entity.PopularSearchesEntity;
import com.medicine.medicine_back.repository.resultSet.GetPopularListResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PopularSearchRepository extends JpaRepository<PopularSearchesEntity, Integer> {
    
    @Query(
            value =
                    "SELECT search_word as searchWord, count(search_word) AS count " +
                            "FROM popular_searches " +
                            "GROUP BY search_word " +
                            "ORDER BY count DESC " +
                            "LIMIT 10 ",
            nativeQuery = true
    )
    List<GetPopularListResultSet> getPopularList();
}
