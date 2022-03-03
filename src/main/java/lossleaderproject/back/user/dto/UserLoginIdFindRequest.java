package lossleaderproject.back.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginIdFindRequest {

    @NotNull(message = "이름을 필수로 입력하셔야 됩니다.")
    private String userName;
    @NotNull(message = "생년월일을 필수로 입력하셔야 됩니다.")
    private String birthDate;
    @Email(message = "이메일 형식에 알맞게 입력하셔야 됩니다.")
    @NotNull(message = "이메일을 필수로 입렧하셔야 됩니다.")
    private String email;
}
