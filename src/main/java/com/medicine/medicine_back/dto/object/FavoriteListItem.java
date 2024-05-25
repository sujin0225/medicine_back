package com.medicine.medicine_back.dto.object;

import com.medicine.medicine_back.repository.resultSet.GetFavoriteResultSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteListItem {
    private String userId;
    private String itemSeq;
    private String ITEM_NAME;
    private String FORM_CODE_NAME;
    private String CLASS_NO;
    private String CLASS_NAME;
    private String ENTP_NAME;
    private String ETC_OTC_NAME;
    private String write_datetime;
    private String ITEM_IMAGE;
    private String ITEM_ENG_NAME;

    public FavoriteListItem(GetFavoriteResultSet resultSet) {
        this.userId = resultSet.getUserId();
        this.itemSeq = resultSet.getItemSeq();
        this.ITEM_NAME = resultSet.getITEM_NAME();
        this.FORM_CODE_NAME = resultSet.getFORM_CODE_NAME();
        this.CLASS_NO = resultSet.getCLASS_NO();
        this.CLASS_NAME = resultSet.getCLASS_NAME();
        this.ENTP_NAME = resultSet.getENTP_NAME();
        this.ETC_OTC_NAME = resultSet.getETC_OTC_NAME();
        this.write_datetime = resultSet.getwrite_datetime();
        this.ITEM_IMAGE = resultSet.getITEM_IMAGE();
        this.ITEM_ENG_NAME = resultSet.getITEM_ENG_NAME();
    }

    public static List<FavoriteListItem> copyList(List<GetFavoriteResultSet> resultSets) {
        List<FavoriteListItem> list = new ArrayList<>();
        for (GetFavoriteResultSet resultSet: resultSets) {
            FavoriteListItem favoriteListItem = new FavoriteListItem(resultSet);
            list.add(favoriteListItem);
        }
        return list;
    }
}
