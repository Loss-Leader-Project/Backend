package lossleaderproject.back.order.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@DynamicInsert
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Long id;


    @Column(updatable = false)
    private LocalDate orderDate;
    private Long orderNumber; // 주문번호
    private String visitTime; // 방문시간*/
    private Integer visitCount; // 방문 인원
    private int payPrice; // 결제 금액
    private int usedMileage; // 사용된 마일리지
    private boolean allUseMileage; // 전액 사용 여부
    private int lastPrice; // 최종 결제 금액
    private boolean orderAgree; // 동의 여부부

    @Column(columnDefinition = "boolean default false")
    private Boolean isReview; // 동의 여부부

    public void now() {
        this.orderDate = LocalDate.now();
    }
    public void orderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }
    public void setReview(Boolean isReview) {this.isReview = isReview;}
    public Orders(Long orderNumber, String visitTime, int visitCount, int payPrice, int usedMileage, int lastPrice, boolean allUseMileage, boolean orderAgree) {
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
