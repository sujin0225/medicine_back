package com.medicine.medicine_back.dto.response.review;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
//import com.medicine.medicine_back.dto.object.ReviewListItem;
import com.medicine.medicine_back.dto.object.ReviewListItem;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.repository.resultSet.GetReviewResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetReviewResponseDto extends ResponseDto {
//    private int reviewNumber;
//    private String userId;
//    private String itemSeq;
//    private String content;
////    private List<String> reviewImageList;
//    private String writeDatetime;
////    private String helpfulCount;
//    private int starRating;

    private List<ReviewListItem> reviewListItems;

    private GetReviewResponseDto(List<GetReviewResultSet> resultSet) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.reviewListItems = ReviewListItem.copyList(resultSet);
//        private List<ReviewListItem> reviewList;
//        List<String> reviewImageList = new ArrayList<>();
//        for (ImageEntity imageEntity: imageEntities) {
//            String reviewImage = imageEntity.getImage();
//            reviewImageList.add(reviewImage);
//        }
//        this.reviewNumber = resultSet.getReviewNumber();
//        this.userId = resultSet.getUserId();
//        this.itemSeq = resultSet.getItemSeq();
//        this.content = resultSet.getContent();
////        this.reviewImageList = reviewImageList;
//        this.writeDatetime = resultSet.getWriteDatetime();
//        this.starRating = resultSet.getStarRating();
    }

//    private List<ReviewListItem> reviewList;
//    private GetReviewResponseDto(List<GetReviewResultSet> resultSet) {
//        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
//        this.reviewList = ReviewListItem.copyList(resultSet);
//    }

    public static ResponseEntity<GetReviewResponseDto> success(List<GetReviewResultSet> resultSet) {
        GetReviewResponseDto result = new GetReviewResponseDto(resultSet);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDto> notExistReview() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_MEDICINE, ResponseMessage.NOT_EXISTED_MEDICINE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

    }
}
