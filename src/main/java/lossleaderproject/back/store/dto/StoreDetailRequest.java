package lossleaderproject.back.store.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.store.entitiy.StoreDetail;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDetailRequest {

    @NotNull(message = "업체 전화번호를 필수로 입력하셔야됩니다.")
    private String storePhoneNumber;
    @NotNull(message = "운영 시간을 필수로 입력하셔야됩니다.")
    private String operatingTime;
    @NotNull(message = "운영 기간을 필수로 입력하셔야됩니다.")
    private String operatingPeriod;
    @NotNull(message = "도로명 주소를 필수로 입력하셔야됩니다.")
    private String roadAddress;
    @NotNull(message = "위도를 필수로 입력하셔야됩니다.")
    private Float latitude;
    @NotNull(message = "겯도를 주소를 필수로 입력하셔야됩니다.")
    private Float longitude;

    public StoreDetail storeDetailRequesttoEntity() {
        return new StoreDetail(this.storePhoneNumber, this.operatingTime, this.operatingPeriod, this.roadAddress, this.latitude, this.longitude);
    }


}
