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
}