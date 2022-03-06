package lossleaderproject.back.review.repository;

import lossleaderproject.back.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
