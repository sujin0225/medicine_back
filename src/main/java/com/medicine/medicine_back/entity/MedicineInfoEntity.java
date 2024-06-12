package com.medicine.medicine_back.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "medicine_info")
@Table(name = "medicine_info")
public class MedicineInfoEntity {
    @Id
    @Column(nullable = false)
    @JsonProperty("itemSeq")
    private String ITEM_SEQ;
    @JsonProperty("efcyQesitm")
    private String efcy_Qesitm;
    @JsonProperty("useMethodQesitm")
    private String use_Method_Qesitm;
    @JsonProperty("atpnWarnQesitm")
    private String atpn_Warn_Qesitm;
    @Column(name = "atpnQesitm")
    @JsonProperty("atpnQesitm")
    private String atpn_Qesitm;
    @JsonProperty("intrcQesitm")
    private String intrc_Qesitm;
    @JsonProperty("depositMethodQesitm")
    private String deposit_Method_Qesitm;
    @JsonProperty("updateDe")
    private String update_De;
    @JsonProperty("openDe")
    private String open_De;
    @JsonProperty("seQesitm")
    private String se_Qesitm;
}
