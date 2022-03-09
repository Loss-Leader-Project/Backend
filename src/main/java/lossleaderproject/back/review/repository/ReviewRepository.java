package lossleaderproject.back.review.repository;

import lossleaderproject.back.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review,Long> {
    Page<Review> findAllByStoreIdOrderByCreateDateAsc(Long storeId, Pageable pageable);
    Page<Review> findAllByUserIdOrderByCreateDateAsc(Long userId, Pageable pageable);
    List<Review> findTop20ByOrderByStarDesc();
}
