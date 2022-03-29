package lossleaderproject.back.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderResponse {
    private Long orderId;
    private String thumbnailImage;
    private String briefAddress;
    private String storeName;
    private String benefitCondition;
    private String couponContent;
    private int priceOfCoupon;
    private int mileage;
    private Long userId;
    private String userName;
    private String phoneNumber;
    private String visitTime;
    private Integer visitCount;
}
