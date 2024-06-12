package com.medicine.medicine_back.dto.response.medicineInfo;

import com.medicine.medicine_back.common.ResponseCode;
import com.medicine.medicine_back.common.ResponseMessage;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.repository.resultSet.GetMedicineInfoResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetMedicineInfoResponseDto extends ResponseDto {
    private String ITEM_SEQ;
    private String efcy_Qesitm;
    private String use_Method_Qesitm;
    private String atpn_Warn_Qesitm;
    private String atpn_Qesitm;
    private String intrc_Qesitm;
    private String deposit_Method_Qesitm;
    private String update_De;
    private String open_De;
    private String se_Qesitm;

    private GetMedicineInfoResponseDto(GetMedicineInfoResultSet resultSet) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.ITEM_SEQ = resultSet.getITEM_SEQ();
        this.efcy_Qesitm = resultSet.getefcy_Qesitm();
        this.use_Method_Qesitm = resultSet.getuse_Method_Qesitm();
        this.atpn_Warn_Qesitm = resultSet.getatpn_Warn_Qesitm();
        this.atpn_Qesitm = resultSet.getatpn_Qesitm();
        this.intrc_Qesitm = resultSet.getintrc_Qesitm();
        this.deposit_Method_Qesitm = resultSet.getdeposit_Method_Qesitm();
        this.update_De = resultSet.getupdate_De();
        this.open_De = resultSet.getopen_De();
        this.se_Qesitm = resultSet.getse_Qesitm();
    }

    //성공
    public static ResponseEntity<GetMedicineInfoResponseDto> success(GetMedicineInfoResultSet resultSet) {
        GetMedicineInfoResponseDto result = new GetMedicineInfoResponseDto(resultSet);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
