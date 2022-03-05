package lossleaderproject.back.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lossleaderproject.back.review.entity.Review;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ReviewRequest {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewPost {
        @NotNull(message = "리뷰 별점은 필수로 입력 되어야 합니다")
        private Float star;
        @NotNull(message = "리뷰 제목은 필수로 입력 되어야 합니다")
        private String title;
        @NotNull(message = "리뷰 내용은 필수로 입력 되어야 합니다")
        private String content;
        @NotNull(message = "이미지들의 식별자 정보는 필수로 있어야 합니다")
        private List<ReviewImageRequest.reviewImagePost> imageIdentifyList;


        public Review reviewRequestToEntity() {
            return new Review(star,title,content);
        }
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageUpload {
        @NotNull(message = "이미지들의 정보는 필수로 있어야 합니다")
        private ReviewImageRequest.imageUpload imageUpload;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class ImageRemove {
        @NotNull(message = "이미지의 식별자 정보는 필수로 있어야 합니다")
        private String imageIdentify;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class ImageUpdate {
        @NotNull(message = "업로드 될 이미지의 정보는 필수로 있어야 합니다")
        private ImageUpload imageUpload;
        @NotNull(message = "삭제 될 이미지의 정보는 필수로 있어야 합니다")
        private ImageRemove imageDelete;
    }


}
