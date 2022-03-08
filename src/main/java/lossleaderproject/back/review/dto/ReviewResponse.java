package lossleaderproject.back.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lossleaderproject.back.review.entity.Review;
import lossleaderproject.back.review.entity.ReviewImage;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ReviewResponse {

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

        }
    }

}
