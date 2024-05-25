package com.medicine.medicine_back.dto.response.review;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.object.FavoriteListItem;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.repository.resultSet.GetFavoriteResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetFavoriteResponseDto extends ResponseDto {
    private List<FavoriteListItem> favoriteListItems;

    private GetFavoriteResponseDto(List<GetFavoriteResultSet> resultSets) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.favoriteListItems = FavoriteListItem.copyList(resultSets);
    }

    //성공
    public static ResponseEntity<GetFavoriteResponseDto> success(List<GetFavoriteResultSet> resultSets) {
        GetFavoriteResponseDto result = new GetFavoriteResponseDto(resultSets);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
