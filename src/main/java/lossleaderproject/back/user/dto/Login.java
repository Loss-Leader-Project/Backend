package lossleaderproject.back.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    @NotNull(message = "아이디를 입력해 주세요.")
    private String loginId;
    @NotNull(message = "비밀번호를 입력해 주세요.")
    private String password;
}
