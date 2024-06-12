package com.medicine.medicine_back.dto.response.medicinePermission;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.repository.resultSet.GetMedicinePermissionResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetMedicinePermissionResponseDto extends ResponseDto {
    private String ITEM_SEQ;
    private String ATC_CODE;
    private String ENTP_ENG_NAME;
    private String BAR_CODE;
    private String MAIN_ITEM_INGR;
    private String MAIN_INGR_ENG;
    private String EDI_CODE;
    private String CHART;
    private String ITEM_PERMIT_DATE;

    private GetMedicinePermissionResponseDto(GetMedicinePermissionResultSet resultSet) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.ITEM_SEQ = resultSet.getITEM_SEQ();
        this.ATC_CODE = resultSet.getATC_CODE();
        this.ENTP_ENG_NAME = resultSet.getENTP_ENG_NAME();
        this.BAR_CODE = resultSet.getBAR_CODE();
        this.MAIN_ITEM_INGR = resultSet.getMAIN_ITEM_INGR();
        this.MAIN_INGR_ENG = resultSet.getMAIN_INGR_ENG();
        this.EDI_CODE = resultSet.getEDI_CODE();
        this.CHART = resultSet.getCHART();
        this.ITEM_PERMIT_DATE = resultSet.getITEM_PERMIT_DATE();
    }

    //성공
    public static ResponseEntity<GetMedicinePermissionResponseDto> success(GetMedicinePermissionResultSet resultSet) {
        GetMedicinePermissionResponseDto result = new GetMedicinePermissionResponseDto(resultSet);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
