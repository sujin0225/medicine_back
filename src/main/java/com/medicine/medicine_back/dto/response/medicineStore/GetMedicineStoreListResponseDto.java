package com.medicine.medicine_back.dto.response.medicineStore;
import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.object.MedicineStoreListItem;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.repository.resultSet.GetMedicineStoreResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@Getter
public class GetMedicineStoreListResponseDto extends ResponseDto {
    private List<MedicineStoreListItem> medicineStoreListItems;
    public GetMedicineStoreListResponseDto(List<GetMedicineStoreResultSet> resultSets) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.medicineStoreListItems = MedicineStoreListItem.copyList(resultSets);
    }

    //판매처 지역으로 검색 성공
    public static ResponseEntity<GetMedicineStoreListResponseDto> success(List<GetMedicineStoreResultSet> resultSets) {
        GetMedicineStoreListResponseDto result = new GetMedicineStoreListResponseDto(resultSets);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
