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
@Entity(name = "medicine_store")
@Table(name ="medicine_store")
public class MedicineStoreEntity {
    @Id
    @Column(nullable = false)
    @JsonProperty("MGTNO")
    private String MGTNO; //관리번호
    @JsonProperty("BPLCNM")
    private String BPLCNM; //상비약 판매처명
    @JsonProperty("DTLSTATENM")
    private String DTLSTATENM; //상세 영업 상태명
    @JsonProperty("TRDSTATENM")
    private String TRDSTATENM; //영업 상태명
    @JsonProperty("RDNWHLADDR")
    private String RDNWHLADDR; //도로명 주소
    @JsonProperty("SITEWHLADDR")
    private String SITEWHLADDR; //지번 주소
    @JsonProperty("X")
    private String X; //좌표정보(X)
    @JsonProperty("Y")
    private String Y; //좌표정보(Y)
}