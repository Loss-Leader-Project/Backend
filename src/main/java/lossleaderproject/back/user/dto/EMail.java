package lossleaderproject.back.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EMail {
    @Email(message = "이메일 형식에 알맞게 입력하셔야됩니다.")
    @NotNull(message = "이메일을 입력하셔야됩니다.")
    private String email;
}
