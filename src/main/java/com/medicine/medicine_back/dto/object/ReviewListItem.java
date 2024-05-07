package com.medicine.medicine_back.dto.object;

import com.medicine.medicine_back.entity.ImageEntity;
import com.medicine.medicine_back.repository.resultSet.GetReviewResultSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class ReviewListItem {
        private int reviewNumber;
        private String userId;
        private String itemSeq;
        private String content;
        private List<String> reviewImageList;
        private String writeDatetime;
        private int helpfulCount;
        private int starRating;

        public ReviewListItem(GetReviewResultSet resultSet, List<ImageEntity> imageEntities) {
            this.reviewNumber = resultSet.getReviewNumber();
            this.userId = resultSet.getUserId();
            this.itemSeq = resultSet.getItemSeq();
            this.content = resultSet.getContent();
            this.reviewImageList = imageEntities.stream()
                    .filter(imageEntity -> imageEntity.getReviewNumber() == this.reviewNumber)
                    .map(ImageEntity::getImage)
                    .collect(Collectors.toList());
            this.writeDatetime = resultSet.getWriteDatetime();
            this.starRating = resultSet.getStarRating();
            this.starRating = resultSet.getStarRating();
        }

        public static List<ReviewListItem> copyList(List<GetReviewResultSet> resultSets, List<ImageEntity> imageEntities) {
            List<ReviewListItem> list = new ArrayList<>();
            for (GetReviewResultSet resultSet: resultSets) {
                ReviewListItem ReviewListItem = new ReviewListItem(resultSet, imageEntities);
                list.add(ReviewListItem);
            }
            return list;
        }
}
