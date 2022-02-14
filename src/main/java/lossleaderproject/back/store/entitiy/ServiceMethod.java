package lossleaderproject.back.store.entitiy;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable // 임베디드 타입을 정의하는 곳에 사용
@Getter
@NoArgsConstructor // 빈생성자
@AllArgsConstructor // 멤버변수를 다 포함한 생성자
public class ServiceMethod {
    private Boolean storeMeal; // 매장에서 식사
    private Boolean packaging; // 포장
    private Boolean delivery; // 배달
}
