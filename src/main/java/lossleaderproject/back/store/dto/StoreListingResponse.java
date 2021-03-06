package lossleaderproject.back.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lossleaderproject.back.store.entitiy.Store;

import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoreListingResponse {
    @NotNull(message = "아이디를 필수로 입력 하셔야 합니다.")
    private Long id;

    @NotNull(message = "간략한 주소를 필수로 입력하셔야됩니다.")
    private String briefAddress;

    @NotNull(message = "가게 명을 필수로 입력 하셔야 합니다.")
    private String storeName;

    @NotNull(message = "썸네일 이미지 url 정보를 필수로 입력 하셔야 합니다.")
    private String thumbnailImage;

    @NotNull(message = "서비스 방법들을 필수로 입력하셔야됩니다.")
    private String  content; // 매장에서 식사
    private Boolean storeMeal; // 매장에서 식사
    private Boolean packaging; // 포장
    private Boolean delivery; // 배달

    @NotNull(message = "리뷰 개수를 필수로 입력하셔야됩니다.")
    private Integer reviewCount;
    @NotNull(message = "평균 별점을 필수로 입력하셔야됩니다.")
    private Float avgStar;

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

    @NotNull(message = "쿠폰 등급 이름을 필수로 입력하셔야 됩니다.")
    private String couponGradeName;

    @NotNull(message = "쿠폰 등급 이름을 필수로 입력하셔야 됩니다.")
    private LocalDateTime createDate;

    public StoreListingResponse(Store store) {
        this.id = store.getId();
        this.briefAddress = store.getBriefAddress();
        this.storeName = store.getStoreName();
        this.thumbnailImage = store.getThumbnailImage();
        this.content = store.getContent();
        this.storeMeal = store.getStoreMeal();
        this.packaging = store.getPackaging();
        this.delivery = store.getDelivery();
        this.reviewCount = store.getReviewCount();
        this.avgStar =store.getAvgStar();
        this.priceOfCoupon = store.getPriceOfCoupon();
        this.benefitCondition = store.getBenefitCondition();
        this.couponContent = store.getCouponContent();
        this.leftCoupon = store.getLeftCoupon();
        this.totalCoupon = store.getTotalCoupon();
        this.couponGradeName = store.getCouponGradeName();
        this.createDate = store.getCreateDate();
    }
}
