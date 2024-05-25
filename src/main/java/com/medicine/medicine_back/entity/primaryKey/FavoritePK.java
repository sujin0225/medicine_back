package com.medicine.medicine_back.entity.primaryKey;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoritePK implements Serializable {
    @Column(name = "ITEM_SEQ")
    private String itemSeq;
    @Column(name = "user_id")
    private String userId;
}
