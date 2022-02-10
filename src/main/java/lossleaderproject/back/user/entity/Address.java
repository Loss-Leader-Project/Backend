package lossleaderproject.back.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Embeddable // 임베디드 타입을 정의하는 곳에 사용
@Getter
@NoArgsConstructor // 빈생성자
@AllArgsConstructor // 멤버변수를 다 포함한 생성자
public class Address {

    private String postalCode; // 우편번호
    private String briefAddress; // 간략주소
    private String detailAddress; // 상세주소
}
