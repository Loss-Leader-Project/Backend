package lossleaderproject.back.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lossleaderproject.back.user.entity.Address;
import lossleaderproject.back.user.entity.User;

import javax.persistence.Embedded;
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

    @NotNull(message = "주소를 필수로 입력하셔야됩니다.")
    @Embedded // 임베디드 타입을 사용하는곳에 사용(생략 가능)
    private Address address;

    @NotNull(message = "생년월일을 필수로 입력하셔야됩니다.")
    private LocalDateTime birthDate;

    private String recommendedPerson;

    private int mileage;


    public User toEntity() {
        return new User(this.loginId, this.password, this.userName, this.phoneNumber, this.email, this.address, this.birthDate, this.recommendedPerson);
    }

}
