package lossleaderproject.back.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDetailResponse {


    @NotNull(message = "아이디를 필수로 입력 하셔야 합니다.")
    private Long id;
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
    @NotNull(message = "경도를 주소를 필수로 입력하셔야됩니다.")
    private Float longitude;
    @NotNull(message = "메뉴 이미지를 필수로 입력하셔야됩니다.")
    private String storeMenuImage;

    private List<StoreFoodImageResponse> storeFoodImageResponseList;
    private List<StoreMenuResponse> storeMenuResponseList;
    private List<StoreHashTagResponse> storeHashTagResponseList;

}
