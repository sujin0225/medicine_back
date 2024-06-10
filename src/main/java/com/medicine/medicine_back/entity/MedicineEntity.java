package com.medicine.medicine_back.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "medicines")
@Table(name = "medicines")
public class MedicineEntity {
    @Id
    @Column(nullable = false)
    @JsonProperty("ITEM_SEQ")
    private String ITEM_SEQ;
    @JsonProperty("ITEM_NAME")
    private String ITEM_NAME;
    @JsonProperty("FORM_CODE_NAME")
    private String FORM_CODE_NAME;
    @JsonProperty("CLASS_NO")
    private String CLASS_NO;
    @JsonProperty("CLASS_NAME")
    private String CLASS_NAME;
    @JsonProperty("ENTP_NAME")
    private String ENTP_NAME;
    @JsonProperty("ETC_OTC_NAME")
    private String ETC_OTC_NAME;
    @JsonProperty("ITEM_IMAGE")
    private String ITEM_IMAGE;
    @JsonProperty("ITEM_ENG_NAME")
    private String ITEM_ENG_NAME;
    @JsonProperty("CHART")
    private String CHART;
    @JsonProperty("DRUG_SHAPE")
    private String DRUG_SHAPE;
    @JsonProperty("THICK")
    private String THICK;
    @JsonProperty("LENG_LONG")
    private String LENG_LONG;
    @JsonProperty("LENG_SHORT")
    private String LENG_SHORT;
    @JsonProperty("PRINT_FRONT")
    private String PRINT_FRONT;
    @JsonProperty("PRINT_BACK")
    private String PRINT_BACK;
    @JsonProperty("LINE_FRONT")
    private String LINE_FRONT;
    @JsonProperty("LINE_BACK")
    private String LINE_BACK;
    @JsonProperty("IMG_REGIST_TS")
    private String IMG_REGIST_TS;
    @JsonProperty("MARK_CODE_FRONT_ANAL")
    private String MARK_CODE_FRONT_ANAL;
    @JsonProperty("MARK_CODE_BACK_ANAL")
    private String MARK_CODE_BACK_ANAL;
    @JsonProperty("MARK_CODE_FRONT_IMG")
    private String MARK_CODE_FRONT_IMG;
    @JsonProperty("MARK_CODE_BACK_IMG")
    private String MARK_CODE_BACK_IMG;
    @JsonProperty("CHANGE_DATE")
    private String CHANGE_DATE;
}