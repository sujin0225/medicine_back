package com.medicine.medicine_back.dto.response.review;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
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

    private int reviewNumber;
    private String itemSeq;
    private String userId;
    private String content;
    private String writeDatetime;
    private int starRating;
    private List<String> reviewImageList;
    private GetReviewResponseDto(GetReviewResultSet resultSet, List<ImageEntity> imageEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        List<String> reviewImageList = new ArrayList<>();
        for (ImageEntity imageEntity: imageEntities) {
            String reviewImage = imageEntity.getImage();
            reviewImageList.add(reviewImage);
        }
        this.reviewNumber = resultSet.getReviewNumber();
        this.itemSeq = resultSet.getItemSeq();
        this.userId = resultSet.getUserId();
        this.content = resultSet.getContent();
        this.writeDatetime = resultSet.getWriteDatetime();
        this.starRating = resultSet.getStarRating();
        this.reviewImageList = reviewImageList;
    }

    public static ResponseEntity<GetReviewResponseDto> success(GetReviewResultSet resultSet, List<ImageEntity> imageEntities) {
        GetReviewResponseDto result = new GetReviewResponseDto(resultSet, imageEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> notExistReview() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_MEDICINE, ResponseMessage.NOT_EXISTED_MEDICINE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
