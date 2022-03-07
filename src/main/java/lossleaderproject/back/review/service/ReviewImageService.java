package lossleaderproject.back.review.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.order.entity.Orders;
import lossleaderproject.back.review.dto.ReviewImageRequest;
import lossleaderproject.back.review.dto.ReviewRequest;
import lossleaderproject.back.review.entity.Review;
import lossleaderproject.back.review.entity.ReviewImage;
import lossleaderproject.back.review.repository.ReviewImageRepository;
import lossleaderproject.back.review.repository.ReviewRepository;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewImageService {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;

    public List<ReviewImage> save(List<ReviewImageRequest.ReviewImagePost> reviewImagePostList) {
        List<ReviewImage> reviewImages = new ArrayList<>();
        for (ReviewImageRequest.ReviewImagePost reviewImagePost : reviewImagePostList) {
            ReviewImage reviewImage = reviewImagePost.reviewImageRequestToEntity();
            reviewImages.add(reviewImage);
            reviewImageRepository.save(reviewImage);
        }
        return reviewImages;
    }
}
