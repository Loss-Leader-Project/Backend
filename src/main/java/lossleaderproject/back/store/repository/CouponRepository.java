package lossleaderproject.back.store.repository;

import lossleaderproject.back.store.entitiy.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository  extends JpaRepository<Coupon, Long> {
}
