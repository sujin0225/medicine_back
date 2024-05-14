package com.medicine.medicine_back.repository.resultSet;

public interface GetReviewResultSet{
    Integer getReviewNumber();
    String getItemSeq();
    String getUserId();
    String getContent();
    String getWriteDatetime();
    Integer getStarRating();
    Integer gethelpfulCount();
}
