package lossleaderproject.back.store.repository;

import lossleaderproject.back.store.entitiy.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface StoreRepository extends JpaRepository<Store, Long> {
    // JPA 쓰라고 하셔서 문서 보고 쿼리 지우고 변경 했습니다.
    // 티어별 JPA 쿼리
    Page<Store> findAllByCouponGradeNameOrderByPriceOfCouponDesc(String CouponGradeName,Pageable pageable);
    Page<Store> findAllByCouponGradeNameOrderByPriceOfCouponAsc(String CouponGradeName,Pageable pageable);
    Page<Store> findAllByCouponGradeNameOrderByCreateDateAsc(String CouponGradeName,Pageable pageable);
    Page<Store> findAllByCouponGradeNameOrderByAvgStarDesc(String CouponGradeName,Pageable pageable);
    // 전체 JPA 쿼리
    Page<Store> findAllByOrderByPriceOfCouponDesc(Pageable pageable);
    Page<Store> findAllByOrderByPriceOfCoupon(Pageable pageable);
    Page<Store> findAllByOrderByCreateDateAsc(Pageable pageable);
    Page<Store> findAllByOrderByAvgStarDesc(Pageable pageable);
    // 핫 플레이스 API 여부는 보류하고 03.07 9시에 결정 하기로 했습니다.
}
