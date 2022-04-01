package lossleaderproject.back.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lossleaderproject.back.review.entity.Review;
import lossleaderproject.back.review.entity.ReviewImage;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewResponse {
    @Getter
    @AllArgsConstructor
    public static class ReviewPost{
        private Long id;
    }

    @Getter
    @NoArgsConstructor
    public static class ReviewListing{
        @NotNull(message = "리뷰 ID는 필수로 출력 되어야 합니다")
        private Long id;
        @NotNull(message = "리뷰 별점은 필수로 출력 되어야 합니다")
        private Float star;
        @NotNull(message = "리뷰 제목은 필수로 출력 되어야 합니다")
        private String title;
        @NotNull(message = "리뷰 내용은 필수로 출력 되어야 합니다")
        private String content;
        @NotNull(message = "유저 이름은 필수로 출력 되어야 합니다")
        private String userName;
        private LocalDateTime createDate;
        private LocalDateTime lastModifiedDate;

        @NotNull(message = "이미지들의 식별자 정보는 필수로 있어야 합니다")
        private List<ReviewImageResponse.ReviewImageToListing> imageIdentifyList;


        public ReviewListing(Review review){
            this.id = review.getId();
            this.star = review.getStar();
            this.title = review.getTitle();
            this.content = review.getContent();
            this.userName = review.getUser().getUserName();
            List<ReviewImageResponse.ReviewImageToListing> imageIdentifies = new ArrayList<>();
            for (ReviewImage reviewImage : review.getReviewImages()) {
                imageIdentifies.add(new ReviewImageResponse.ReviewImageToListing(reviewImage));
            }
            this.imageIdentifyList = imageIdentifies;
            this.createDate = review.getCreateDate();
            this.lastModifiedDate = review.getLastModifiedDate();

        }
    }

    @Getter
    @NoArgsConstructor
    public static class ReviewListingUser{
        @NotNull(message = "리뷰 ID는 필수로 출력 되어야 합니다")
        private Long id;
        @NotNull(message = "리뷰 별점은 필수로 출력 되어야 합니다")
        private Float star;
        @NotNull(message = "리뷰 제목은 필수로 출력 되어야 합니다")
        private String title;
        @NotNull(message = "리뷰 내용은 필수로 출력 되어야 합니다")
        private String content;
        @NotNull(message = "유저 이름은 필수로 출력 되어야 합니다")
        private String userName;
        private Long storeId;
        private String storeName;
        private String briefAddress;
        private LocalDateTime createDate;
        private LocalDateTime lastModifiedDate;

        @NotNull(message = "이미지들의 식별자 정보는 필수로 있어야 합니다")
        private List<ReviewImageResponse.ReviewImageToListing> imageIdentifyList;


        public ReviewListingUser(Review review){
            this.id = review.getId();
            this.star = review.getStar();
            this.title = review.getTitle();
            this.content = review.getContent();
            this.userName = review.getUser().getUserName();
            this.storeId = review.getStore().getId();
            this.storeName = review.getStore().getStoreName();
            this.briefAddress = review.getStore().getBriefAddress();
            this.createDate = review.getCreateDate();
            this.lastModifiedDate = review.getLastModifiedDate();
            List<ReviewImageResponse.ReviewImageToListing> imageIdentifies = new ArrayList<>();
            for (ReviewImage reviewImage : review.getReviewImages()) {
                imageIdentifies.add(new ReviewImageResponse.ReviewImageToListing(reviewImage));
            }
            this.imageIdentifyList = imageIdentifies;

        }
    }

    @Getter
    @AllArgsConstructor
    public static class ReviewListingHotPlace{
        private Long id;
        private Long storeId;
        private String reviewTitle;
        private String userName;
        private String reviewContent;
        private String reviewImage;
        private Float star;


        public ReviewListingHotPlace(Review review){
            this.id = review.getId();
            this.storeId = review.getStore().getId();
            this.reviewTitle = review.getTitle();
            this.star = review.getStar();
            this.reviewContent = review.getContent();
            this.userName = review.getUser().getUserName();
            if(review.getReviewImages().size()>0) {
                this.reviewImage = review.getReviewImages().get(0).getImageIdentify();
            }

        }
    }
    @Getter
    @NoArgsConstructor
    public static class ReviewListingMyPage{
        private Page<ReviewListingUser> reviewListing;
        private Float avgStar;
        public ReviewListingMyPage(Page<ReviewListingUser> reviewListing,Float avgStar){
            this.reviewListing = reviewListing;
            this.avgStar = avgStar;
        }


    }



}