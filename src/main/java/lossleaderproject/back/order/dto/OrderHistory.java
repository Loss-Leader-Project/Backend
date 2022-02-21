package lossleaderproject.back.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistory {
    private String userName;
    private int mileage;
    private LocalDateTime orderDate;
    private int orderNumber;
    private String briefAddress;
    private String storeName;
    private String couponContent;
    private Integer priceOfCoupon;
}
