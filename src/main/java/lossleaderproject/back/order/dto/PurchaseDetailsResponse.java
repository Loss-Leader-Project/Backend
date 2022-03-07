package lossleaderproject.back.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PurchaseDetailsResponse {
    private PurchaseHistory purchaseHistory;
    private PurchaseUserInfo purchaseUserInfo;

}
