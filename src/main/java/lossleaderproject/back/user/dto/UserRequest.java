package lossleaderproject.back.user.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lossleaderproject.back.user.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter

@NoArgsConstructor
public class UserRequest {
    @NotNull(message = "아이디를 필수로 입력하셔야됩니다.")
    private String loginId;

    @NotNull(message = "비밀번호를 필수로 입력하셔야됩니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 16자의 비밀번호여야 합니다.")
    private String password;

    @NotNull(message = "비밀번호를 확인해야합니다.")
    private String confirmPassword;

    @NotNull(message = "이름을 필수로 입력하셔야됩니다.")
    private String userName;

    @NotNull(message = "핸드폰 번호를 필수로 입력하셔야됩니다.")
    private String phoneNumber;

    @Email(message = "이메일 형식에 맞게 입력하셔야 됩니다.")
    private String email;

    @NotNull(message = "인증 번호를 입력해주셔야 됩니다.")
    private Integer emailCertification;

    @NotNull(message = "우편번호를 입력하셔야 됩니다.")
    private String postalCode; // 우편번호
    @NotNull(message = "간략하게 주소를 입력하셔야 됩니다.")
    private String briefAddress; // 간략주소
    @NotNull(message = "상세주소를 입력해주셔야 됩니다.")
    private String detailAddress; // 상세주소

    @NotNull(message = "생년월일을 필수로 입력하셔야됩니다.")
    private String birthDate;

    private String recommendedPerson;

  

    public UserRequest(String loginId, String userName, String password, String email, String phoneNumber) {
        this.loginId = loginId;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public UserRequest(String loginId, String password, String userName, String email) {
        this.loginId = loginId;
        this.password = password;
        this.userName = userName;
        this.email = email;
    }

    public User kakaoOAuthToEntity() {
        return new User(this.loginId, this.password, this.userName, this.email);
    }

    public User naverOAuthToEntity() {
        return new User(this.loginId, this.userName, this.password, this.phoneNumber, this.email);
    }

    public User toEntity() {
        return new User(this.loginId, this.password, this.userName, this.phoneNumber, this.email, this.postalCode, this.briefAddress, this.detailAddress, this.birthDate, this.recommendedPerson);
    }

}
