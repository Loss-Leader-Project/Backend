package lossleaderproject.back.error.reviewException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

public class ReviewControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException me) {
        Map<String, String> errors = new HashMap<>();
        me.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ReviewCustomException.class)
    protected ResponseEntity<ReviewErrorResponse> handleReviewCustomException(ReviewCustomException e) {
        return ReviewErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
