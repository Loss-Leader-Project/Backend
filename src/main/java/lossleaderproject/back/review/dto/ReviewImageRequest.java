package lossleaderproject.back.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lossleaderproject.back.review.entity.Review;
import lossleaderproject.back.review.entity.ReviewImage;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
public class ReviewImageRequest {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class imageUpload {
        @NotNull(message = "리뷰 이미지는 필수로 입력 되어야 합니다")
        private MultipartFile image;

        private String imageIdentify;
    }
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class reviewImagePost {
        @NotNull(message = "리뷰 이미지는 필수로 입력 되어야 합니다")
        private String imageIdentify;

        public ReviewImage reviewImageRequestToEntity() {
            return new ReviewImage(imageIdentify);
        }
    }
}
