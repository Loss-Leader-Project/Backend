package lossleaderproject.back.store.entitiy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lossleaderproject.back.user.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;


@Entity
@Getter
@RequiredArgsConstructor
@DynamicInsert
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String briefAddress;
    private String storeName;
    private String thumbnailImage;

    @Column(columnDefinition = "integer default 0")
    private Integer reviewCount;

    @Column(columnDefinition = "Float default 0")
    private Float avgStar;

    @Embedded // 임베디드 타입을 사용하는곳에 사용(생략 가능)
    private ServiceMethod serviceMethod;
//    private Coupon coupon;

    //public void setStoreCoupon(Coupon coupon) {
//        this.coupon = coupon;
//    }

    private Integer priceOfCoupon; // 쿠폰 가격
    private String couponContent; // 쿠폰 설명
    private String benefitCondition; // 혜택 조건
    private Integer leftCoupon; // 남은 쿠폰
    private Integer totalCoupon; // 전체 쿠폰
    private Integer couponGrade; // 쿠폰 등급
    private String couponGradeName;


    public Store(String briefAddress, String storeName, String thumbnailImage, ServiceMethod serviceMethod, Integer priceOfCoupon, String couponContent, String benefitCondition, Integer leftCoupon, Integer totalCoupon, Integer couponGrade, String couponGradeName) {
        this.briefAddress = briefAddress;
        this.storeName = storeName;
        this.thumbnailImage = thumbnailImage;
        this.serviceMethod = serviceMethod;
        this.priceOfCoupon = priceOfCoupon;
        this.couponContent = couponContent;
        this.benefitCondition = benefitCondition;
        this.leftCoupon = leftCoupon;
        this.totalCoupon = totalCoupon;
        this.couponGrade = couponGrade;
        this.couponGradeName = couponGradeName;
    }
}
