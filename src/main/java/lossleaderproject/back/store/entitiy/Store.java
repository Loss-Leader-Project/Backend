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

    private String  content; // 매장에서 식사
    private Boolean storeMeal; // 매장에서 식사
    private Boolean packaging; // 포장
    private Boolean delivery; // 배달
    private Integer priceOfCoupon; // 쿠폰 가격
    private String couponContent; // 쿠폰 설명
    private String benefitCondition; // 혜택 조건
    private Integer leftCoupon; // 남은 쿠폰
    private Integer totalCoupon; // 전체 쿠폰
    private String couponGradeName;

    public void minusLeftCoupon() {
        leftCoupon -=    1;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public void setAvgStar(Float avgStar) {
        this.avgStar = avgStar;
    }

    public Store(String briefAddress, String storeName, String thumbnailImage, String content, Boolean storeMeal,Boolean packaging,Boolean delivery, Integer priceOfCoupon, String couponContent, String benefitCondition, Integer leftCoupon, Integer totalCoupon, String couponGradeName) {
        this.briefAddress = briefAddress;
        this.storeName = storeName;
        this.thumbnailImage = thumbnailImage;
        this.content = content;
        this.storeMeal = storeMeal;
        this.packaging = packaging;
        this.delivery = delivery;
        this.priceOfCoupon = priceOfCoupon;
        this.couponContent = couponContent;
        this.benefitCondition = benefitCondition;
        this.leftCoupon = leftCoupon;
        this.totalCoupon = totalCoupon;
        this.couponGradeName = couponGradeName;
    }
}
