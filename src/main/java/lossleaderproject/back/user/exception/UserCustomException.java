package lossleaderproject.back.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCustomException extends RuntimeException{
    private final ErrorCode errorCode;
}
