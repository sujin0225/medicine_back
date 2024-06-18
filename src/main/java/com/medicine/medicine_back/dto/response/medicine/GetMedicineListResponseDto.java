package com.medicine.medicine_back.dto.response.medicine;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.object.MedicineListItem;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.entity.MedicineEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetMedicineListResponseDto extends ResponseDto {
    private int page;
    private int totalPages;
    private int totalCount;
    private List<MedicineListItem>  medicineListItems;

    private GetMedicineListResponseDto(List<MedicineEntity> medicineEntities, int page, int totalPages, int totalCount, String item_name) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.page = page;
        this.totalPages = totalPages;
        this.totalCount = totalCount;
        this.medicineListItems = MedicineListItem.getMedicineList(medicineEntities);
    }
    public static ResponseEntity<GetMedicineListResponseDto> success(List<MedicineEntity> medicineEntities, int page, int totalPages, int totalCount, String item_name) {
        GetMedicineListResponseDto result = new GetMedicineListResponseDto(medicineEntities, page, totalPages, totalCount, item_name);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
