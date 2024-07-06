package com.medicine.medicine_back.dto.object;

import com.medicine.medicine_back.entity.MedicineEntity;
import com.medicine.medicine_back.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MedicineListItem {
    private String ITEM_SEQ;
    private String ITEM_NAME;
    private String FORM_CODE_NAME;
    private String ENTP_NAME;
    private String CLASS_NAME;
    private String ETC_OTC_NAME;
    private String ITEM_IMAGE;

    public MedicineListItem(MedicineEntity medicineEntity) {
        this.ITEM_SEQ = medicineEntity.getITEM_SEQ();
        this.ITEM_NAME = medicineEntity.getITEM_NAME();
        this.FORM_CODE_NAME = medicineEntity.getFORM_CODE_NAME();
        this.ENTP_NAME = medicineEntity.getENTP_NAME();
        this.CLASS_NAME = medicineEntity.getCLASS_NAME();
        this.ETC_OTC_NAME = medicineEntity.getETC_OTC_NAME();
        this.ITEM_IMAGE = medicineEntity.getITEM_IMAGE();
    }

    public static List<MedicineListItem> getMedicineList(List<MedicineEntity> medicineEntities) {
        List<MedicineListItem> list = new ArrayList<>();
        for (MedicineEntity medicineEntity: medicineEntities) {
            MedicineListItem medicineListItem = new MedicineListItem(medicineEntity);
            list.add(medicineListItem);
        }
        return list;
    }
}
