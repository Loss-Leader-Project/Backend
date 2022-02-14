package lossleaderproject.back.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lossleaderproject.back.store.entitiy.Coupon;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CouponRequest {
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




}
