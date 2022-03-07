package lossleaderproject.back.review.repository;

import lossleaderproject.back.review.entity.Review;
import lossleaderproject.back.review.entity.ReviewImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImage,Long> {
}
