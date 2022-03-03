package lossleaderproject.back.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class EmailVerification {
    @NotNull(message = "인증번호를 필수로 입력하셔야됩니다.")
    private int number;
}
