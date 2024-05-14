package com.medicine.medicine_back.dto.object;

import com.medicine.medicine_back.repository.resultSet.GetHelpfulListResultSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HelpfulListItem {
    private String userId;

    public HelpfulListItem(GetHelpfulListResultSet resultSet) {
        this.userId = resultSet.getUserId();
    }

    public static List<HelpfulListItem> copyList(List<GetHelpfulListResultSet> resultSets) {
        List<HelpfulListItem> list = new ArrayList<>();
        for(GetHelpfulListResultSet resultSet: resultSets) {
            HelpfulListItem favoriteListItem = new HelpfulListItem(resultSet);
            list.add(favoriteListItem);
        }
        return list;
    }
}
