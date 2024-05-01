package com.medicine.medicine_back.common;

public interface ResponseMessage {
    String SUCCESS = "성공";
    String VALIDATION_FAIL = "유효성 검사 실패";
    String DUPLICATE_ID = "중복된 아이디";
    String SIGN_IN_FAIL = "로그인 실패";
    String CERTIFICATION_FAIL = "이메일 인증 번호 불일치";
    String MAIL_FAIL = "메일 전송 실패";
    String DATABASE_ERROR = "데이터베이스 오류";
    String NOT_EXISTED_MEDICINE = "존재하지 않는 의약품";
    String NOT_EXISTED_USER = "존재하지 않는 유저";
}
