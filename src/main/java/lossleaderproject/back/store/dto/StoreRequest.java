package lossleaderproject.back.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lossleaderproject.back.store.entitiy.Coupon;
import lossleaderproject.back.store.entitiy.ServiceMethod;
import lossleaderproject.back.store.entitiy.Store;
import org.hibernate.annotations.ColumnDefault;


import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreRequest {


    @NotNull(message = "간략한 주소를 필수로 입력하셔야됩니다.")
    private String briefAddress;

    @NotNull(message = "가게 명을 필수로 입력 하셔야 합니다.")

    private String storeName;

    @NotNull(message = "썸네일 이미지 url 정보를 필수로 입력 하셔야 합니다.")
    private String thumbnailImageUrl;

    @NotNull(message = "서비스 방법들을 필수로 입력하셔야됩니다.")
    @Embedded // 임베디드 타입을 사용하는곳에 사용(생략 가능)
    private ServiceMethod serviceMethod;

    private Integer reviewCount;

    private Float avgStar;

    private Coupon coupon;

    @NotNull(message = "쿠폰 가격을 필수로 입력하셔야됩니다.")
    private Integer priceOfCoupon;

    @NotNull(message = "혜택 조건을 필수로 입력하셔야됩니다.")
    private String benefitCondition;

    @NotNull(message = "혜택 내용을 필수로 입력하셔야 합니다.")
    private String couponContent;

    @NotNull(message = "잔여 쿠폰 수를 필수로 입력하셔야됩니다.")
    private Integer leftCoupon;

    @NotNull(message = "총 쿠폰 수를 필수로 입력하셔야됩니다.")
    private Integer totalCoupon;

    @NotNull(message = "쿠폰 등급을 필수로 입력하셔야 됩니다.")
    private Integer couponGrade;


    public Store storeRequesttoEntity() {
        return new Store(this.briefAddress, this.storeName, this.thumbnailImageUrl, this.serviceMethod);
    }

    public Coupon couponRequestToEntity() {
        return new Coupon(this.priceOfCoupon, this.benefitCondition, this.couponContent, this.leftCoupon, this.totalCoupon, this.couponGrade);
    }

}
