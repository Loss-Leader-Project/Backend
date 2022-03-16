package lossleaderproject.back.review.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.minio.MinioService;
import lossleaderproject.back.order.entity.Orders;
import lossleaderproject.back.order.repository.OrderRepository;
import lossleaderproject.back.review.dto.ReviewImageRequest;
import lossleaderproject.back.review.dto.ReviewRequest;
import lossleaderproject.back.review.dto.ReviewResponse;
import lossleaderproject.back.review.entity.Review;
import lossleaderproject.back.review.repository.ReviewRepository;
import lossleaderproject.back.security.auth.PrincipalDetails;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.store.repository.StoreRepository;
import lossleaderproject.back.store.service.StoreService;
import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ReviewService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImageService reviewImageService;
    private final StoreService storeService;
    private final MinioService minioService;

    public void save(PrincipalDetails principalDetails, Long orderNumber, Long storeId, ReviewRequest.ReviewPost reviewPost) {
        User user = userRepository.findByLoginId(principalDetails.getUsername());
        Orders orders = orderRepository.findByOrderNumber(orderNumber);
        Store store = storeRepository.findById(storeId).get();
        Review review = reviewPost.reviewRequestToEntity();
        review.setOrders(orders);
        review.setUser(user);
        review.setStore(store);
        // 업체 리뷰 평점 갱신
        storeService.review(storeId,reviewPost.getStar());
        // 리뷰 이미지 식별자(uuid) 저장
        review.setReviewImages(reviewImageService.save(reviewPost.getImageIdentifyList()));
        // 리뷰 저장
        reviewRepository.save(review);
    }

    public String imageUpload(ReviewImageRequest.ImageUpload imageUpload ) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String imageIdentify = UUID.randomUUID().toString()+".jpg";
        imageUpload.setImageIdentify(imageIdentify);
        minioService.imageUpload(
                "review",
                imageIdentify,
                imageUpload.getImage().getInputStream());

        return  imageIdentify;

    }

    public String imageRemove(ReviewImageRequest.ImageRemove imageDelete ) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        minioService.imageRemove(
                "review",
                imageDelete.getImageIdentify());
        return  imageDelete.getImageIdentify();
    }

    public String imageUpdate(ReviewImageRequest.ImageUpdate imageUpdate) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String imageIdentify = UUID.randomUUID().toString()+".jpg";
        imageUpdate.getImageUpload().setImageIdentify(imageIdentify);
        minioService.imageRemove(
                "review",
                imageUpdate.getImageRemove().getImageIdentify());

        minioService.imageUpload(
                "review",
                imageIdentify,
                imageUpdate.getImageUpload().getImage().getInputStream());

        return imageIdentify;
    }

    public Page< ReviewResponse.ReviewListing> findAllByStoreIdOrderByCreateDateAsc(Long storeId, Pageable pageable){
        Page<Review> reviews = reviewRepository.findAllByStoreIdOrderByCreateDateAsc(storeId,pageable);
        return reviews.map(review -> new ReviewResponse.ReviewListing(review));
    }

    public Page< ReviewResponse.ReviewListing> findAllByUserIdOrderByCreateDateAsc(PrincipalDetails principalDetails, Pageable pageable) {
        User user = userRepository.findByLoginId(principalDetails.getUsername());
        Page<Review> reviews = reviewRepository.findAllByUserOrderByCreateDateAsc(user, pageable);
        return reviews.map(review -> new ReviewResponse.ReviewListing(review));
    }
    public List<ReviewResponse.ReviewListingHotPlace> findTop20ByOrderByStarDesc(){
        List<Review> reviews = reviewRepository.findTop20ByOrderByStarDesc();
        List<ReviewResponse.ReviewListingHotPlace> reviewsToListing = new ArrayList<>();
        for (Review review : reviews) {
            reviewsToListing.add(new ReviewResponse.ReviewListingHotPlace(review));
        }
        return reviewsToListing;
    }
}
