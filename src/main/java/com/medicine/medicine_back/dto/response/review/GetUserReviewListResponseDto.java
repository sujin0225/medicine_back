package com.medicine.medicine_back.dto.response.review;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.object.ReviewListItem;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.entity.ImageEntity;
import com.medicine.medicine_back.repository.resultSet.GetReviewResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetUserReviewListResponseDto extends ResponseDto {
    private List<ReviewListItem> reviewListItems;

    private GetUserReviewListResponseDto(List<GetReviewResultSet> resultSets, List<ImageEntity> imageEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.reviewListItems = ReviewListItem.copyList(resultSets, imageEntities);
    }

    //성공
    public static ResponseEntity<GetUserReviewListResponseDto> success(List<GetReviewResultSet> resultSet, List<ImageEntity> imageEntities) {
        GetUserReviewListResponseDto result = new GetUserReviewListResponseDto(resultSet, imageEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //존재하지 않는 리뷰
    public static ResponseEntity<ResponseDto> notExistReview() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_REVIEW, ResponseMessage.NOT_EXISTED_REVIEW);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
