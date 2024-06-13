package com.medicine.medicine_back.dto.object;

import com.medicine.medicine_back.entity.MedicineStoreEntity;
import com.medicine.medicine_back.repository.resultSet.GetMedicineStoreResultSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MedicineStoreListItem {
    private String MGTNO;
    private String BPLCNM;
    private String DTLSTATENM;
    private String TRDSTATENM;
    private String RDNWHLADDR;
    private String SITEWHLADDR;
    private String X;
    private String Y;

    public MedicineStoreListItem(GetMedicineStoreResultSet resultSet) {
        this.MGTNO = resultSet.getMGTNO();
        this.BPLCNM = resultSet.getBPLCNM();
        this.DTLSTATENM = resultSet.getDTLSTATENM();
        this.TRDSTATENM = resultSet.getTRDSTATENM();
        this.RDNWHLADDR = resultSet.getRDNWHLADDR();
        this.SITEWHLADDR = resultSet.getSITEWHLADDR();
        this.X = resultSet.getX();
        this.Y = resultSet.getY();
    }

    public static List<MedicineStoreListItem> copyList(List<GetMedicineStoreResultSet> resultSets) {
        List<MedicineStoreListItem> list = new ArrayList<>();
        for (GetMedicineStoreResultSet resultSet: resultSets) {
            MedicineStoreListItem medicineStoreListItem = new MedicineStoreListItem(resultSet);
            list.add(medicineStoreListItem);
        }
        return list;
    }
}
