package lossleaderproject.back.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lossleaderproject.back.order.entity.Orders;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
public class OrderRequest {
    private String thumbnailImage;
    private String briefAddress;
    private String storeName;
    private String couponContent;
    private Integer priceOfCoupon;
    @NotNull(message = "구매자를 입력해주셔야 됩니다.")
    private String userName;
    @NotNull(message = "전화번호를 입력해주셔야 됩니다.")
    private String phoneNumber;

    private Long orderNumber;
    @NotNull(message = "방문시간을 입력해주셔야 됩니다.")
    private String visitTime;
    @NotNull(message = "방문인원을 입력해주셔야 됩니다.")
    private Integer visitCount;
    private int payPrice;

    private int usedMileage;
    private boolean allUseMileage;
    private int lastPrice;
    private boolean orderAgree;

    public void payPriceofCoupon(int price) {
        this.payPrice = price;
    }
    public void calcLastPrice(int payPrice, int usedMileage) {
        this.lastPrice = payPrice - usedMileage;
    }

    public void setUsedMileage(int usedMileage) {
        this.usedMileage = usedMileage;
    }

    public OrderRequest(String userName, String phoneNumber, Long orderNumber, String visitTime, Integer visitCount, int usedMileage, boolean allUseMileage, boolean orderAgree) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.orderNumber = orderNumber;
        this.visitTime = visitTime;
        this.visitCount = visitCount;
        this.usedMileage = usedMileage;
        this.allUseMileage = allUseMileage;
        this.orderAgree = orderAgree;
    }

    public void order(String thumbnailImage,String briefAddress, String storeName, String couponContent, Integer priceOfCoupon, String userName, String phoneNumber) {
        this.thumbnailImage = thumbnailImage;
        this.briefAddress = briefAddress;
        this.storeName = storeName;
        this.couponContent = couponContent;
        this.priceOfCoupon = priceOfCoupon;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

    public Orders toEntity() {
        return (new Orders(getOrderNumber(), getVisitTime(), getVisitCount(), getPayPrice(), getUsedMileage(), getLastPrice(), isAllUseMileage(), isOrderAgree()));
    }

}
