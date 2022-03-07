package lossleaderproject.back.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PurchaseUserInfo {
    private String userName;
    private String phoneNumber;
    private String visitTime;
    private Integer visitCount;
}
