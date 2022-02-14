package lossleaderproject.back.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lossleaderproject.back.user.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotNull(message = "아이디를 필수로 입력하셔야됩니다.")
    private String loginId;

    @NotNull(message = "비밀번호를 필수로 입력하셔야됩니다.")
    private String password;

    @NotNull(message = "비밀번호를 확인해야합니다.")
    private String confirmPassword;

    @NotNull(message = "이름을 필수로 입력하셔야됩니다.")
    private String userName;

    @NotNull(message = "핸드폰 번호를 필수로 입력하셔야됩니다.")
    private String phoneNumber;

    @Email(message = "이메일 형식에 맞게 입력하셔야 됩니다.")
    private String email;

    @NotNull(message = "우편번호를 입력하셔야 됩니다.")
    private String postalCode; // 우편번호
    @NotNull(message = "간략하게 주소를 입력하셔야 됩니다.")
    private String briefAddress; // 간략주소
    @NotNull(message = "상세주소를 입력해주셔야 됩니다.")
    private String detailAddress; // 상세주소

    @NotNull(message = "생년월일을 필수로 입력하셔야됩니다.")
    private LocalDateTime birthDate;

    private String recommendedPerson;

    private int mileage;


    public User toEntity() {
        return new User(this.loginId, this.password, this.userName, this.phoneNumber, this.email, this.postalCode,this.briefAddress ,this.detailAddress,this.birthDate, this.recommendedPerson);
    }

}
