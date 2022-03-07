package lossleaderproject.back.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PurchaseHistory {
    private LocalDate orderDate;
    private Long orderNumber;
    private String couponContent;
    private int price;
}
