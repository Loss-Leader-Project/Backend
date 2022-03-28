package lossleaderproject.back.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

public class UserResponse {
    @Getter
    @NoArgsConstructor
    public static class MyPageUserInfo {
        private String loginId;
        private String userName;
        private String oldPassword;
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,16}",
                message = "비밀번호는 영문 소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 16자의 비밀번호여야 합니다.")
        private String newPassword;
        private String newPasswordConfirm;
        private String email;
        private String phoneNumber;
        private String birthDate;
        private String recommendedPerson;
        private String role;

        public MyPageUserInfo(String loginId, String userName, String email, String phoneNumber, String birthDate, String recommendedPerson, String role) {
            this.loginId = loginId;
            this.userName = userName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.birthDate = birthDate;
            this.recommendedPerson = recommendedPerson;
            this.role = role;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class MyPageForOrderListing {
        private String loginId;
        private String userName;
        private Integer mileage;
    }

}

