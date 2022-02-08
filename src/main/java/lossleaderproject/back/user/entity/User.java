package lossleaderproject.back.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lossleaderproject.back.user.dto.UserRequest;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(updatable = false)
    private String loginId;

    private String password;

    private String userName;

    private String phoneNumber;
    private String email;
    @Embedded // 임베디드 타입을 사용하는곳에 사용(생략 가능)
    private Address address;

    @Column(updatable = false)
    private LocalDateTime birthDate;

    private String recommendedPerson;


    private int mileage;

    // 3레이어
    // entity 가장 상위
    public User(String loginId, String password, String userName, String phoneNumber, String email, Address address, LocalDateTime birthDate, String recommendedPerson) {
        this.loginId = loginId;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;
        this.recommendedPerson = recommendedPerson;
        this.mileage = 3000;
    }
}
