package lossleaderproject.back.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lossleaderproject.back.store.entitiy.Store;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreRequest {

    @NotNull(message = "간략한 주소를 필수로 입력하셔야됩니다.")
    private String briefAddress;

    @NotNull(message = "가게 명을 필수로 입력 하셔야 합니다.")
    private String storeName;

    @NotNull(message = "썸네일 이미지 정보를 필수로 입력 하셔야 합니다.")
    private MultipartFile thumbnailImage;

    private String thumbnailImageIdentify;

    @NotNull(message = "서비스 방법들을 필수로 입력하셔야됩니다.")
    private String  content; // 매장에서 식사
    private Boolean storeMeal; // 매장에서 식사
    private Boolean packaging; // 포장
    private Boolean delivery; // 배달


    private Integer reviewCount;

    private Float avgStar;

    private StoreDetailRequest storeDetailRequest;

    @NotNull(message = "쿠폰 가격을 필수로 입력하셔야됩니다.")
    private Integer priceOfCoupon;

    @NotNull(message = "혜택 조건을 필수로 입력하셔야됩니다.")
    private String benefitCondition;

    @NotNull(message = "혜택 내용을 필수로 입력하셔야 합니다.")
    private String couponContent;

    @NotNull(message = "잔여 쿠폰 수를 필수로 입력하셔야됩니다.")
    private Integer leftCoupon;

    @NotNull(message = "총 쿠폰 수를 필수로 입력하셔야됩니다.")
    private Integer totalCoupon;

    @NotNull(message = "쿠폰 등급을 필수로 입력하셔야 됩니다.")
    private Integer couponGrade;

    @NotNull(message = "쿠폰 등급 이름을 필수로 입력하셔야 됩니다.")
    private String couponGradeName;

    public Store storeRequestToEntity() {
        return new Store(
                this.briefAddress,
                this.storeName,
                this.thumbnailImageIdentify,
                this.content,
                this.storeMeal,
                this.packaging,
                this.delivery,
                this.priceOfCoupon,
                this.benefitCondition,
                this.couponContent,
                this.leftCoupon,
                this.totalCoupon,
                this.couponGrade,
                this.couponGradeName);
    }

}
