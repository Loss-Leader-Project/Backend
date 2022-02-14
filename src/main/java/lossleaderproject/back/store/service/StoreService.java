package lossleaderproject.back.store.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.store.dto.CouponRequest;
import lossleaderproject.back.store.dto.StoreRequest;
import lossleaderproject.back.store.entitiy.Coupon;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.store.repository.CouponRepository;
import lossleaderproject.back.store.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;
    private final CouponRepository couponRepository;

    // userId로 반환
    public Long save(StoreRequest storeRequest) {
        Coupon coupon = couponRepository.save(storeRequest.couponRequestToEntity());
        Store store = storeRequest.storeRequesttoEntity(); // 스토어 엔티티로 변경
        store.savedCoupon(coupon);
        Store savedStore = storeRepository.save(store);
        return savedStore.getId();
    }

    public Page<Store> findAllSilverDate(Pageable pageable) {
        return storeRepository.findAllSilverDate(pageable);
    }

    public Page<Store> findAllSilverStar(Pageable pageable) {
        return storeRepository.findAllSilverStar(pageable);
    }

    public Page<Store> findAllSilverPriceASC(Pageable pageable) {
        return storeRepository.findAllSilverPriceASC(pageable);
    }

    public Page<Store> findAllSilverPriceDESC(Pageable pageable) {
        return storeRepository.findAllSilverPriceDESC(pageable);
    }

    public Page<Store> findAllGoldDate(Pageable pageable) {
        return storeRepository.findAllGoldDate(pageable);
    }

    public Page<Store> findAllGoldStar(Pageable pageable) {
        return storeRepository.findAllGoldStar(pageable);
    }

    public Page<Store> findAllGoldPriceASC(Pageable pageable) {
        return storeRepository.findAllGoldPriceASC(pageable);
    }

    public Page<Store> findAllGoldPriceDESC(Pageable pageable) {
        return storeRepository.findAllGoldPriceDESC(pageable);
    }
}
