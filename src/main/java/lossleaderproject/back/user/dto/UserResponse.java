package lossleaderproject.back.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lossleaderproject.back.user.entity.User;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserResponse {
    private String loginId;
    private String userName;
    private String oldPassword;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 15자의 비밀번호여야 합니다.")
    private String newPassword;
    private String newPasswordConfirm;
    private String email;
    private String phoneNumber;
    private String birthDate;
    private String recommendedPerson;

    public UserResponse(String loginId, String userName, String email, String phoneNumber, String birthDate, String recommendedPerson) {
        this.loginId = loginId;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.recommendedPerson = recommendedPerson;
    }


}
