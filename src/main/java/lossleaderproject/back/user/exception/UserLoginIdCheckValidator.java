package lossleaderproject.back.user.exception;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.user.dto.UserRequest;
import lossleaderproject.back.user.repository.UserRepository;
import lossleaderproject.back.user.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserLoginIdCheckValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object object, Errors errors) {
        UserRequest userRequest = (UserRequest) object;
        if(userRepository.existsByLoginId(userRequest.getLoginId())) {
            errors.rejectValue("loginId",null,"아이디중복");
        }
        if(!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            errors.rejectValue("confirmPassword",null,"비밀번호가 일치하지 않습니다.");
        }
    }
}
