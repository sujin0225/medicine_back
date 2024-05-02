package com.medicine.medicine_back.repository.resultSet;

import java.util.List;

public interface GetReviewResultSet{
    Integer getReviewNumber();
    String getItemSeq();
    String getUserId();
    String getContent();
    String getWriteDatetime();
    Integer getStarRating();
}
