package lossleaderproject.back.error.reviewException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter

@AllArgsConstructor
public enum ReviewErrorCode {

    DUPLICATE_ID(HttpStatus.CONFLICT, "이미 리뷰가 된 주문입니다.");


    private final HttpStatus httpStatus;
    private final String detail;
}
