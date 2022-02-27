package lossleaderproject.back.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter

@AllArgsConstructor
public enum ErrorCode {
    RECOMMENDED_USER_NOT_FOUND(HttpStatus.NOT_FOUND,"추천인 아이디가 존재하지 않습니다."),
    NO_EXIST_USERNAME_BIRTHDATE_EMAIL(HttpStatus.NOT_FOUND,"사용자 이름, 생년월일, 이메일을 잘못입력하셨습니다."),


    DUPLICATE_ID(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    DISMATCH_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지않습니다.");



    private final HttpStatus httpStatus;
    private final String detail;
}
