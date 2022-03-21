package lossleaderproject.back.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@DynamicInsert
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

    @Column(columnDefinition = "Float default 0")
    private Float avgStar; // 평균 별점

    @Column(columnDefinition = "Integer default 0")
    private Integer reviewCount; // 리뷰 개수

    @Column(updatable = false)
    private String birthDate;

    private String recommendedPerson;

    private int mileage;
    private String role;

    public void setAvgStar(Float avgStar) {
        this.avgStar = avgStar;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public User (String loginId,String userName,String password, String phoneNumber,String email) {
        this.phoneNumber = phoneNumber;
        this.loginId = loginId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = "ROLE_NAVER";
    }

    public User(String loginId, String password, String userName, String email) {
        this.loginId = loginId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.role = "ROLE_KAKAO";
    }

    public void encodePassword(String password) {
        this.password = password;
    }
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

    public void userInfoEditBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void userInfoRecommendPerson(String recommendedPerson) {
        this.recommendedPerson = recommendedPerson;

    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // 3레이어
    // entity 가장 상위
    public User(String loginId, String password, String userName, String phoneNumber, String email, String postalCode, String briefAddress, String detailAddress, String birthDate, String recommendedPerson) {
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
        this.role = "ROLE_USER";
    }
}
