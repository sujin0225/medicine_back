package com.medicine.medicine_back.dto.object;

import com.medicine.medicine_back.repository.resultSet.GetReviewResultSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class ReviewListItem {
        private int reviewNumber;
        private String userId;
        private String itemSeq;
        private String content;
//        private List<String> reviewImageList;
        private String writeDatetime;
        //    private String helpfulCount;
        private int starRating;

        public ReviewListItem(GetReviewResultSet resultSet) {
            this.reviewNumber = resultSet.getReviewNumber();
            this.userId = resultSet.getUserId();
            this.itemSeq = resultSet.getItemSeq();
            this.content = resultSet.getContent();
            this.writeDatetime = resultSet.getWriteDatetime();
            this.starRating = resultSet.getStarRating();
        }

        public static List<ReviewListItem> copyList(List<GetReviewResultSet> resultSets) {
            List<ReviewListItem> list = new ArrayList<>();
            for (GetReviewResultSet resultSet: resultSets) {
                ReviewListItem ReviewListItem = new ReviewListItem(resultSet);
                list.add(ReviewListItem);
            }
            return list;
        }
}
