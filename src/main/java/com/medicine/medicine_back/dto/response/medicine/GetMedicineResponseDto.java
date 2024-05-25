package com.medicine.medicine_back.dto.response.medicine;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.repository.resultSet.GetMedicineResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetMedicineResponseDto extends ResponseDto {
    private String ITEM_SEQ;
    private String ITEM_NAME;
    private String ITEM_ENG_NAME;
    private String FORM_CODE_NAME;
    private String CLASS_NO;
    private String CLASS_NAME;
    private String ENTP_NAME;
    private String ETC_OTC_NAME;
    private String ITEM_IMAGE;

    private GetMedicineResponseDto(GetMedicineResultSet resultSet) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.ITEM_SEQ = resultSet.getITEM_SEQ();
        this.ITEM_NAME = resultSet.getITEM_NAME();
        this.ITEM_ENG_NAME = resultSet.getITEM_ENG_NAME();
        this.FORM_CODE_NAME = resultSet.getFORM_CODE_NAME();
        this.CLASS_NO = resultSet.getCLASS_NO();
        this.CLASS_NAME = resultSet.getCLASS_NAME();
        this.ENTP_NAME = resultSet.getENTP_NAME();
        this.ETC_OTC_NAME = resultSet.getETC_OTC_NAME();
        this.ITEM_IMAGE = resultSet.getITEM_IMAGE();
    }

    //성공
    public static ResponseEntity<GetMedicineResponseDto> success(GetMedicineResultSet resultSet) {
        GetMedicineResponseDto result = new GetMedicineResponseDto(resultSet);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
