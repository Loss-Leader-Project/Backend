package lossleaderproject.back.error.reviewException;

import lombok.Builder;
import lombok.Getter;
import lossleaderproject.back.error.userException.UserErrorCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
@Getter
@Builder
public class ReviewErrorResponse {
    private final int status;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<ReviewErrorResponse> toResponseEntity(ReviewErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ReviewErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getDetail())
                        .build());
    }
}
