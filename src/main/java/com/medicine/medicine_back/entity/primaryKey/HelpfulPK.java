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
public class HelpfulPK implements Serializable {
    @Column(name = "user_id")
    private String userId;
    @Column(name = "review_number")
    private int reviewNumber;
}
