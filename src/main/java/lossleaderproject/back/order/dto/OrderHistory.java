package lossleaderproject.back.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistory {
    private LocalDate orderDate;
    private Long orderNumber;
    private String briefAddress;
    private Long storeId;
    private String storeName;
    private String couponContent;
    private Integer priceOfCoupon;
}
