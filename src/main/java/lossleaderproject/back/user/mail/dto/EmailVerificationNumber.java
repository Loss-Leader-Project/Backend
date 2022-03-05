package lossleaderproject.back.user.mail.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class EmailVerificationNumber {
    @NotNull(message = "인증번호를 입력해주세요.")
    private int number;
}
