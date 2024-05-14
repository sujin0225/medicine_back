package com.medicine.medicine_back.entity;

import com.medicine.medicine_back.entity.primaryKey.HelpfulPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "helpful")
@Table(name = "helpful")
@IdClass(HelpfulPK.class)
public class HelpfulEntity {
    @Id
    private String userId;
    @Id
    private int reviewNumber;
}
