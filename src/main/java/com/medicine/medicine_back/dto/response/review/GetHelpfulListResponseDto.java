package com.medicine.medicine_back.dto.response.review;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.object.HelpfulListItem;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.repository.resultSet.GetHelpfulListResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetHelpfulListResponseDto extends ResponseDto {

    private List<HelpfulListItem> HelpfulList;

    private GetHelpfulListResponseDto(List<GetHelpfulListResultSet> resultSets) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.HelpfulList = HelpfulListItem.copyList(resultSets);
    }

    //성공
    public static ResponseEntity<GetHelpfulListResponseDto> success(List<GetHelpfulListResultSet> resultSets) {
        GetHelpfulListResponseDto result = new GetHelpfulListResponseDto(resultSets);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //존재하지 않는 리뷰
    public static ResponseEntity<ResponseDto> noExistReview() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_REVIEW, ResponseMessage.NOT_EXISTED_REVIEW);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
