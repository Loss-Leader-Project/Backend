package lossleaderproject.back.error.reviewException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewCustomException extends RuntimeException{
    private final ReviewErrorCode errorCode;
}
