package com.medicine.medicine_back.dto.response.review;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
//import com.medicine.medicine_back.dto.object.ReviewListItem;
import com.medicine.medicine_back.dto.object.ReviewListItem;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.entity.ImageEntity;
import com.medicine.medicine_back.repository.resultSet.GetReviewResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetReviewResponseDto extends ResponseDto {
    private List<ReviewListItem> reviewListItems;

    private GetReviewResponseDto(List<GetReviewResultSet> resultSet, List<ImageEntity> imageEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.reviewListItems = ReviewListItem.copyList(resultSet, imageEntities);
    }
    public static ResponseEntity<GetReviewResponseDto> success(List<GetReviewResultSet> resultSet, List<ImageEntity> imageEntities) {
        GetReviewResponseDto result = new GetReviewResponseDto(resultSet, imageEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDto> notExistReview() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_MEDICINE, ResponseMessage.NOT_EXISTED_MEDICINE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
