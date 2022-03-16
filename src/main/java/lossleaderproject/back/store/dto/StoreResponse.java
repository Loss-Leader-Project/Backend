package lossleaderproject.back.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lossleaderproject.back.store.entitiy.Store;

import javax.validation.constraints.NotNull;
import java.util.List;


public class StoreResponse {
    @Getter
    @NoArgsConstructor
    public static class StoreRes {
        @NotNull(message = "아이디를 필수로 입력 하셔야 합니다.")
        private Long id;

    @NotNull(message = "간략한 주소를 필수로 입력하셔야됩니다.")
    private String briefAddress;

        @NotNull(message = "가게 명을 필수로 입력 하셔야 합니다.")
        private String storeName;

        @NotNull(message = "썸네일 이미지 url 정보를 필수로 입력 하셔야 합니다.")
        private String thumbnailImage;

        @NotNull(message = "서비스 방법들을 필수로 입력하셔야됩니다.")
        private String content; // 매장에서 식사
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

        private StoreDetailResponse.StoreDetailPosting storeDetailResponse;

        public StoreRes(Store store, StoreDetailResponse.StoreDetailPosting storeDetailResponse) {
            this.id = store.getId();
            this.briefAddress = store.getBriefAddress();
            this.storeName = store.getStoreName();
            this.thumbnailImage = store.getThumbnailImage();
            this.content = store.getContent();
            this.storeMeal = store.getStoreMeal();
            this.packaging = store.getPackaging();
            this.delivery = store.getDelivery();
            this.reviewCount = store.getReviewCount();
            this.avgStar = store.getAvgStar();
            this.priceOfCoupon = store.getPriceOfCoupon();
            this.benefitCondition = store.getBenefitCondition();
            this.couponContent = store.getCouponContent();
            this.leftCoupon = store.getLeftCoupon();
            this.totalCoupon = store.getTotalCoupon();
            this.couponGradeName = store.getCouponGradeName();
            this.storeDetailResponse = storeDetailResponse;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class StoreHotplace {
        @NotNull(message = "아이디를 필수로 입력 하셔야 합니다.")
        private Long id;

        @NotNull(message = "간략한 주소를 필수로 입력하셔야됩니다.")
        private String location;

        @NotNull(message = "가게 명을 필수로 입력 하셔야 합니다.")
        private String storeName;

        @NotNull(message = "썸네일 이미지 url 정보를 필수로 입력 하셔야 합니다.")
        private String imgUrl;

        @NotNull(message = "혜택 내용을 필수로 입력하셔야 합니다.")
        private String couponContent;

        public StoreHotplace(Store store) {
            this.id = store.getId();
            this.location = store.getBriefAddress();
            this.storeName = store.getStoreName();
            this.couponContent = store.getCouponContent();
            this.imgUrl = store.getThumbnailImage();
        }
    }
    @Setter
    @Getter
    @NoArgsConstructor
    public static class StoreTopData {
        private String briefAddress;
        private String storeName;
        private Float avgStar;
        private Integer priceOfCoupon;
        private String benefitCondition;
        private String couponContent;
        private Integer leftCoupon;
        private Integer totalCoupon;
        private String couponGradeName;
        private List<StoreFoodImageResponse> storeFoodImageResponseList;
        private List<StoreHashTagResponse> storeHashTagResponseList;
        public StoreTopData(Store store,List<StoreFoodImageResponse> storeFoodImageResponseList, List<StoreHashTagResponse> storeHashTagResponseList) {
            this.briefAddress = store.getBriefAddress();
            this.storeName = store.getStoreName();
            this.avgStar = store.getAvgStar();
            this.priceOfCoupon = store.getPriceOfCoupon();
            this.benefitCondition = store.getBenefitCondition();
            this.couponContent = store.getCouponContent();
            this.leftCoupon = store.getLeftCoupon();
            this.totalCoupon = store.getTotalCoupon();
            this.couponGradeName = store.getCouponGradeName();
            this.storeFoodImageResponseList = storeFoodImageResponseList;
            this.storeHashTagResponseList = storeHashTagResponseList;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class StoreDetailPageRes {
        @NotNull(message = "아이디를 필수로 입력 하셔야 합니다.")
        private Long id;
        private StoreTopData storeTopData;
        private StoreDetailResponse.StoreDetailForDetailPage storeDetailResponse;
        public StoreDetailPageRes(Long id ,StoreTopData storeTopData, StoreDetailResponse.StoreDetailForDetailPage storeDetailResponse) {
            this.id = id;
            this.storeTopData = storeTopData;
            this.storeDetailResponse = storeDetailResponse;
        }
    }
}