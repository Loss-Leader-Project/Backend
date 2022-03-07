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

public class ReviewImageRequest {


    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewImagePost {
        @NotNull(message = "리뷰 이미지는 필수로 입력 되어야 합니다")
        private String imageIdentify;

        public ReviewImage reviewImageRequestToEntity() {
            return new ReviewImage(this.imageIdentify);
        }
    }


    @Setter
    @Getter
    @NoArgsConstructor
    public static class ImageUpload {
        @NotNull(message = "리뷰 이미지는 필수로 입력 되어야 합니다")
        private MultipartFile image;

        private String imageIdentify;
    }
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageRemove {
        @NotNull(message = "이미지의 식별자 정보는 필수로 있어야 합니다")
        private String imageIdentify;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageUpdate {
        @NotNull(message = "업로드 될 이미지의 정보는 필수로 있어야 합니다")
        private ImageUpload imageUpload;
        @NotNull(message = "삭제 될 이미지의 정보는 필수로 있어야 합니다")
        private ImageRemove imageRemove;
    }
}
