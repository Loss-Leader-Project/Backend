package lossleaderproject.back.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lossleaderproject.back.user.entity.User;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserResponse {
    public String loginId;
    public String userName;
    public String email;
    public String phoneNumber;
    public LocalDateTime birthDate;
    public String recommendedPerson;

    public UserResponse(String loginId, String userName, String email, String phoneNumber, LocalDateTime birthDate, String recommendedPerson) {
        this.loginId = loginId;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.recommendedPerson = recommendedPerson;
    }



}
