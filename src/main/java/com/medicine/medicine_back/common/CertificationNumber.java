package com.medicine.medicine_back.common;

//인증 번호 4자리 생성
public class CertificationNumber {
    public static String getCertification() {
        String certificationNumber = "";
        for (int count = 0; count < 4; count++) certificationNumber += (int)(Math.random() * 10);
        return certificationNumber;
    }
}
