package lossleaderproject.back.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lossleaderproject.back.user.dto.UserRequest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String loginId;

    private String password;

    private String userName;

    private String phoneNumber;
    private String email;
    private String postalCode; // 우편번호
    private String briefAddress; // 간략주소
    private String detailAddress; // 상세주소

    @Column(updatable = false)
    private LocalDateTime birthDate;

    private String recommendedPerson;

    private int mileage;

    public void restMileage(int usedMileage) {
        this.mileage -= usedMileage;
    }

    public void recommendedMileage() {
        this.mileage += 500;
    }



    public void userInfoEditUserName(String userName) {
        this.userName = userName;
    }

    public void userInfoEditEmail(String email) {
        this.email = email;
    }

    public void userInfoEditPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void userInfoEditBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }
    public void userInfoRecommendPerson(String recommendedPerson) {
        this.recommendedPerson = recommendedPerson;

    }

    // 3레이어
    // entity 가장 상위
    public User(String loginId, String password, String userName, String phoneNumber, String email, String postalCode, String briefAddress, String detailAddress, LocalDateTime birthDate, String recommendedPerson) {
        this.loginId = loginId;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.postalCode = postalCode;
        this.briefAddress = briefAddress;
        this.detailAddress = detailAddress;
        this.birthDate = birthDate;
        this.recommendedPerson = recommendedPerson;
        this.mileage = 3000;
    }
}
