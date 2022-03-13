package lossleaderproject.back.review.controller;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.review.dto.ReviewImageRequest;
import lossleaderproject.back.review.dto.ReviewRequest;
import lossleaderproject.back.review.dto.ReviewResponse;
import lossleaderproject.back.review.entity.Review;
import lossleaderproject.back.review.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    public final ReviewService reviewService;

    @PostMapping("/{userId}/{orderNumber}/{storeId}")
    public ResponseEntity<String> review(@PathVariable("userId") Long userId, @PathVariable("orderNumber") Long orderNumber, @PathVariable("storeId") Long storeId,@RequestBody ReviewRequest.ReviewPost reviewRequest) {
        reviewService.save(userId,orderNumber,storeId,reviewRequest);
        return new ResponseEntity<>("리뷰 작성 완료", HttpStatus.OK);
    }

    @PostMapping("/image-upload")
    public ResponseEntity<String> imageUpload(ReviewImageRequest.ImageUpload imageUpload) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return new ResponseEntity<>(reviewService.imageUpload(imageUpload),HttpStatus.OK);
    }

    @DeleteMapping("/image-delete")
    public ResponseEntity<String> imageDelete(ReviewImageRequest.ImageRemove imageRemove) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return  new ResponseEntity<>(reviewService.imageRemove(imageRemove),HttpStatus.OK);
    }

    @PutMapping("/image-update")
    public ResponseEntity<String> imageUpdate(ReviewImageRequest.ImageUpdate imageUpdate) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return new ResponseEntity<>(reviewService.imageUpdate(imageUpdate),HttpStatus.OK);
    }

    @GetMapping("/listing-store/")
    public ResponseEntity<Page< ReviewResponse.ReviewListing>> findAllByStoreIdOrderByCreateDateAsc(@RequestParam(value = "storeId",required = false) Long storeId, Pageable pageable) {
        return ResponseEntity.ok(reviewService.findAllByStoreIdOrderByCreateDateAsc(storeId,pageable));

    }

    @GetMapping("/listing-user/")
    public ResponseEntity<Page< ReviewResponse.ReviewListing>> findAllByUserIdOrderByCreateDateAsc(@RequestParam(value = "userId",required = false) Long userId, Pageable pageable) {
        return ResponseEntity.ok(reviewService.findAllByUserIdOrderByCreateDateAsc(userId, pageable));
    }
    @GetMapping("/listing-hot")
    public ResponseEntity<List<ReviewResponse.ReviewListingHotPlace>> findAllHotPlace() {
        return ResponseEntity.ok(reviewService.findTop20ByOrderByStarDesc());
    }
}
