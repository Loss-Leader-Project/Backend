package lossleaderproject.back.order.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    private int orderNumber; // 주문번호
    private LocalTime visitTime; // 방문시간*/
    private Integer visitCount; // 방문 인원
    private int payPrice; // 결제 금액
    private int usedMileage; // 사용된 마일리지
    private boolean allUseMileage; // 전액 사용 여부
    private int lastPrice; // 최종 결제 금액

    private boolean orderAgree; // 동의 여부부




    public Orders(int orderNumber, LocalTime visitTime, int visitCount, int payPrice, int usedMileage, int lastPrice, boolean allUseMileage, boolean orderAgree) {
        this.orderNumber = orderNumber;
        this.visitTime = visitTime;
        this.visitCount = visitCount;
        this.payPrice = payPrice;
        this.usedMileage = usedMileage;
        this.allUseMileage = allUseMileage;
        this.lastPrice = lastPrice;
        this.orderAgree = orderAgree;
    }
}
