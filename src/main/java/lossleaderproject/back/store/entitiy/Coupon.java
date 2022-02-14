package lossleaderproject.back.store.entitiy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Coupon extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Integer priceOfCoupon; // 쿠폰 가격
    private String couponContent; // 쿠폰 설명
    private String benefitCondition; // 혜택 조건
    private Integer leftCoupon; // 남은 쿠폰
    private Integer totalCoupon; // 전체 쿠폰
    private Integer couponGrade; // 쿠폰 등급

    public Coupon(Integer priceOfCoupon,String benefitCondition, String couponContent, Integer leftCoupon, Integer totalCoupon, Integer couponGrade) {
        this.priceOfCoupon = priceOfCoupon;
        this.couponContent = couponContent;
        this.benefitCondition = benefitCondition;
        this.leftCoupon = leftCoupon;
        this.totalCoupon = totalCoupon;
        this.couponGrade = couponGrade;
    }
}


