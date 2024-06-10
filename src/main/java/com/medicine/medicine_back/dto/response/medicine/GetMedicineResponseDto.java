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
    private String CHART;
    private String DRUG_SHAPE;
    private String THICK;
    private String LENG_LONG;
    private String LENG_SHORT;
    private String PRINT_FRONT;
    private String PRINT_BACK;
    private String LINE_FRONT;
    private String LINE_BACK;
    private String IMG_REGIST_TS;
    private String MARK_CODE_FRONT_ANAL;
    private String MARK_CODE_BACK_ANAL;
    private String MARK_CODE_FRONT_IMG;
    private String MARK_CODE_BACK_IMG;
    private String CHANGE_DATE;

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
        this.CHART = resultSet.getCHART();
        this.DRUG_SHAPE = resultSet.getDRUG_SHAPE();
        this.THICK = resultSet.getTHICK();
        this.LENG_LONG = resultSet.getLENG_LONG();
        this.LENG_SHORT = resultSet.getLENG_SHORT();
        this.PRINT_FRONT = resultSet.getPRINT_FRONT();
        this.PRINT_BACK = resultSet.getPRINT_BACK();
        this.LINE_FRONT = resultSet.getLINE_FRONT();
        this.LINE_BACK = resultSet.getLINE_BACK();
        this.IMG_REGIST_TS = resultSet.getIMG_REGIST_TS();
        this.MARK_CODE_FRONT_ANAL = resultSet.getMARK_CODE_FRONT_ANAL();
        this.MARK_CODE_BACK_ANAL = resultSet.getMARK_CODE_BACK_ANAL();
        this.MARK_CODE_FRONT_IMG = resultSet.getMARK_CODE_FRONT_IMG();
        this.MARK_CODE_BACK_IMG = resultSet.getMARK_CODE_BACK_IMG();
        this.CHANGE_DATE = resultSet.getCHANGE_DATE();
    }

    //성공
    public static ResponseEntity<GetMedicineResponseDto> success(GetMedicineResultSet resultSet) {
        GetMedicineResponseDto result = new GetMedicineResponseDto(resultSet);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
