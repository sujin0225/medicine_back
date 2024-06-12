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
@Entity(name = "medicinepermission")
@Table(name = "medicinepermission")
public class MedicinePermissionEntity {
    @Id
    @Column(nullable = false)
    @JsonProperty("ITEM_SEQ")
    private String ITEM_SEQ;
    @JsonProperty("ATC_CODE")
    private String ATC_CODE;
    @JsonProperty("ENTP_ENG_NAME")
    private String ENTP_ENG_NAME;
    @JsonProperty("BAR_CODE")
    private String BAR_CODE;
    @JsonProperty("MAIN_ITEM_INGR")
    private String MAIN_ITEM_INGR;
    @JsonProperty("MAIN_INGR_ENG")
    private String MAIN_INGR_ENG;
    @JsonProperty("EDI_CODE")
    private String EDI_CODE;
    @JsonProperty("CHART")
    private String CHART;
    @JsonProperty("ITEM_PERMIT_DATE")
    private String ITEM_PERMIT_DATE;
}
