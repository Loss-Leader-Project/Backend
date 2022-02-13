package lossleaderproject.back.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lossleaderproject.back.order.entity.Orders;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
public class OrderRequest {
    @NotNull(message = "구매자를 입력해주셔야 됩니다.")
    private String userName;
    @NotNull(message = "전화번호를 입력해주셔야 됩니다.")
    private String phoneNumber;

    private int orderNumber;
    @NotNull(message = "방문시간을 입력해주셔야 됩니다.")
    private LocalTime visitTime;
    @NotNull(message = "방문인원을 입력해주셔야 됩니다.")
    private Integer visitCount;
    private int payPrice;

    private int usedMileage;
    private boolean allUseMileage;
    private int lastPrice;
    private boolean orderAgree;
    public void calcLastPrice(int payPrice, int usedMileage ) {
        this.lastPrice =  payPrice - usedMileage;
    }
    public void setUsedMileage(int usedMileage) {
        this.usedMileage = usedMileage;
    }
    public OrderRequest(String userName, String phoneNumber, int orderNumber, LocalTime visitTime, Integer visitCount, int payPrice, int usedMileage,boolean  allUseMileage,boolean orderAgree) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.orderNumber = orderNumber;
        this.visitTime = visitTime;
        this.visitCount = visitCount;
        this.payPrice = payPrice;
        this.usedMileage = usedMileage;
        this.allUseMileage = allUseMileage;
        this.orderAgree = orderAgree;
    }

    public Orders toEntity() {
        return (new Orders(getOrderNumber(), getVisitTime(), getVisitCount(), getPayPrice(), getUsedMileage(), getLastPrice(),isAllUseMileage() ,isOrderAgree()));
    }

}
