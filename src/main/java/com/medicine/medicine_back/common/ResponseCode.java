package com.medicine.medicine_back.common;

public interface ResponseCode {

    String SUCCESS = "SU"; //성공
    String VALIDATION_FAIL = "VF"; //유효성 검증 실패
    String DUPLICATE_ID = "DI"; //중복된 아이디
    String SIGN_IN_FAIL = "SF"; //로그인 실패
    String CERTIFICATION_FAIL = "CF"; //이메일 인증번호 불일치
    String MAIL_FAIL = "MF"; //메일전송 실패
    String DATABASE_ERROR = "DBE"; //데이터베이스 오류
    String NOT_EXISTED_MEDICINE = "NM"; //존재하지 않는 의약품
    String NOT_EXISTED_USER = "NU"; //존재하지 않는 유저
}
