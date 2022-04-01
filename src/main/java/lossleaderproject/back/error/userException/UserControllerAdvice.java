package lossleaderproject.back.error.userException;

import lossleaderproject.back.error.userException.UserCustomException;
import lossleaderproject.back.error.userException.UserErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserControllerAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException me) {
        Map<String, String> errors = new HashMap<>();
        me.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UserCustomException.class)
    protected ResponseEntity<UserErrorResponse> handleUserCustomException(UserCustomException e) {
        return UserErrorResponse.toResponseEntity(e.getErrorCode());
    }

}
