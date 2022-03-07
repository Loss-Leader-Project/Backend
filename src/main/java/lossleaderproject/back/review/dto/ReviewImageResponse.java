package lossleaderproject.back.review.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lossleaderproject.back.review.entity.Review;
import lossleaderproject.back.review.entity.ReviewImage;

import javax.validation.constraints.NotNull;


public class ReviewImageResponse {

    @Getter
    @NoArgsConstructor
    public static class ReviewImageToListing{
        @NotNull(message = "리뷰 ID는 필수로 출력 되어야 합니다")
        private Long id;
        @NotNull(message = "리뷰 별점은 필수로 출력 되어야 합니다")
        private String imageIdentify;

        public ReviewImageToListing(ReviewImage reviewImage){
            this.id = reviewImage.getId();
            this.imageIdentify = reviewImage.getImageIdentify();
        }
    }
}
