package com.medicine.medicine_back.common;

public interface ResponseMessage {
    String SUCCESS = "성공";
    String VALIDATION_FAIL = "유효성 검사 실패";
    String DUPLICATE_ID = "중복된 아이디";
    String SIGN_IN_FAIL = "로그인 실패";
    String CERTIFICATION_FAIL = "이메일 인증 번호 불일치";
    String DATABASE_ERROR = "데이터베이스 오류";
}
