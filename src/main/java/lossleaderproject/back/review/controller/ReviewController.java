package lossleaderproject.back.review.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lossleaderproject.back.review.dto.ReviewImageRequest;
import lossleaderproject.back.review.dto.ReviewRequest;
import lossleaderproject.back.review.dto.ReviewResponse;
import lossleaderproject.back.review.service.ReviewService;
import lossleaderproject.back.security.auth.PrincipalDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@Api(tags = "리뷰에 대한 API")
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    public final ReviewService reviewService;
    @ApiOperation(value = "리뷰 등록 (리뷰 작성 페이지)")
    @PostMapping("")
    public ResponseEntity<String> review(@ApiIgnore @AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam("orderNumber") Long orderNumber, @RequestParam("storeId") Long storeId, @RequestBody ReviewRequest.ReviewPost reviewRequest) {
        reviewService.save(principalDetails,orderNumber,storeId,reviewRequest);
        return new ResponseEntity<>("리뷰 작성 완료", HttpStatus.OK);
    }

    @ApiOperation(value = "리뷰 이미지 업로드 (리뷰 작성 페이지)", notes = "리뷰 작성할때 이미지 업로드 (리뷰 작성 페이지)")
    @PostMapping("/image-upload")
    public ResponseEntity<String> imageUpload(ReviewImageRequest.ImageUpload imageUpload) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return new ResponseEntity<>(reviewService.imageUpload(imageUpload),HttpStatus.OK);
    }


    @ApiOperation(value = "리뷰 이미지 삭제 (리뷰 작성 페이지)", notes = "리뷰 작성할때 이미지 삭제 (리뷰 작성 페이지)")
    @DeleteMapping("/image-delete")
    public ResponseEntity<String> imageDelete(ReviewImageRequest.ImageRemove imageRemove) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return  new ResponseEntity<>(reviewService.imageRemove(imageRemove),HttpStatus.OK);
    }

    @ApiOperation(value = "리뷰 이미지 수정 (리뷰 작성 페이지)", notes = "리뷰 작성할때 이미지 수정 (리뷰 작성 페이지)")
    @PutMapping("/image-update")
    public ResponseEntity<String> imageUpdate(ReviewImageRequest.ImageUpdate imageUpdate) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return new ResponseEntity<>(reviewService.imageUpdate(imageUpdate),HttpStatus.OK);
    }

    @ApiOperation(value = "리뷰 리스팅 (업체 리스팅 페이지)",notes = "업체 상세보기에서 해당 업체의 모든리뷰 리스팅 (업체 리스팅 페이지)" )
    @GetMapping("/listing-store")
    public ResponseEntity<Page< ReviewResponse.ReviewListing>> findAllByStoreIdOrderByCreateDateAsc(@RequestParam(value = "storeId",required = false) Long storeId, @ApiIgnore Pageable pageable) {
        return ResponseEntity.ok(reviewService.findAllByStoreIdOrderByCreateDateAsc(storeId,pageable));

    }


    @ApiOperation(value = "리뷰 리스팅 (마이페이지) ",notes = "마이페이지 리뷰에서 사용자의 모든리뷰 리스팅 (마이 페이지)" )
    @GetMapping("/listing-user")
    public ResponseEntity<ReviewResponse.ReviewListingMyPage> findAllByUserIdOrderByCreateDateAsc(@ApiIgnore @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                                                   @ApiIgnore Pageable pageable,
                                                                                                   @RequestParam(value = "filter",required = false,defaultValue = "Date") String filter,
                                                                                                   @RequestParam(value = "sorting",required = false,defaultValue = "DESC") String sorting) {
        return ResponseEntity.ok(reviewService.findAllByUserIdOrderByCreateDateAsc(principalDetails, pageable,filter,sorting));
    }
    @ApiOperation(value = "핫 플레이스 리뷰 리스팅 (메인 페이지 핫 플레이스)")
    @GetMapping("/listing-hot")
    public ResponseEntity<List<ReviewResponse.ReviewListingHotPlace>> findAllHotPlace() {
        return ResponseEntity.ok(reviewService.findTop20ByOrderByStarDesc());
    }
}
