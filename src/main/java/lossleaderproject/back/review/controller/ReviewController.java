package lossleaderproject.back.review.controller;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.review.dto.ReviewRequest;
import lossleaderproject.back.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage/review")
public class ReviewController {

    public final ReviewService reviewService;

    @PostMapping("/{userId}/{orderNumber}/{storeId}")
    public ResponseEntity<String> review(@PathVariable("userId") Long userId, @PathVariable("orderNumber") int orderNumber, @PathVariable("storeId") Long storeId,@RequestBody ReviewRequest.ReviewPost reviewRequest) {
        reviewService.save(userId,orderNumber,storeId,reviewRequest);
        return new ResponseEntity<>("리뷰 작성 완료", HttpStatus.OK);
    }

    @PostMapping("/image-upload")
    public ResponseEntity<String> imageUpload(ReviewRequest.ImageUpload imageUpload) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return new ResponseEntity<>(reviewService.imageUpload(imageUpload),HttpStatus.OK);
    }

    @DeleteMapping("/image-delete")
    public ResponseEntity<String> imageDelete(ReviewRequest.ImageRemove imageRemove) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return  new ResponseEntity<>(reviewService.imageRemove(imageRemove),HttpStatus.OK);
    }

    @PutMapping("/image-update")
    public ResponseEntity<String> imageUpdate(ReviewRequest.ImageUpdate imageUpdate) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return new ResponseEntity<>(reviewService.imageUpdate(imageUpdate),HttpStatus.CREATED);
    }
}
