package lossleaderproject.back.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter

@AllArgsConstructor
public enum ErrorCode {
    RECOMMENDED_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "추천인 아이디가 존재하지 않습니다."),
    NO_EXIST_USERNAME_BIRTHDATE_EMAIL(HttpStatus.NOT_FOUND, "사용자 이름, 생년월일, 이메일을 잘못입력하셨습니다."),
    EMAIL_SESSION_INVALIDATE(HttpStatus.NOT_FOUND, "유효시간이 지났습니다. 인증번호를 다시 받으세요. "),
    NOT_EXIST_USER(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),


    NOT_AGREE(HttpStatus.BAD_REQUEST, "동의를 하셔야합니다."),
    NOT_SATISFY_MONEY(HttpStatus.BAD_REQUEST, "금액이 부족합니다."),
    RECONFIRM_NUMBER(HttpStatus.BAD_REQUEST, "인증번호를 재확인하시기 바랍니다."),
    NO_MATCH_NUMBER(HttpStatus.BAD_REQUEST, "인증이 실패하였습니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    DUPLICATE_ID(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),


    DISMATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지않습니다."),
    BAD_REQUEST_LOGINID_PASSWORD(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호가 틀리셨습니다");


    private final HttpStatus httpStatus;
    private final String detail;
}
