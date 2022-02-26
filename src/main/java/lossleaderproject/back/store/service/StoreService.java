package lossleaderproject.back.store.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.store.dto.StoreListingResponse;
import lossleaderproject.back.store.dto.StoreRequest;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.store.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;


    // userId로 반환
    public Store save(StoreRequest storeRequest) {
        //Coupon coupon = couponRepository.findById(couponId).get();
        Store store = storeRequest.storeRequestToEntity();
        //store.setStoreCoupon(coupon);
        Store stores = storeRepository.save(store);
        return stores;
    }

    public Page<StoreListingResponse> findAllSilverDate(Pageable pageable) {
        Page<Store> stores = storeRepository.findAllSilverDate(pageable);
        System.out.println("----------------------------------------------------");
        System.out.println("pageStoreOrder.getContent() = " + stores.getContent());
        return stores.map(store -> new StoreListingResponse(store));
    }

    public Page<StoreListingResponse> findAllSilverStar(Pageable pageable) {
        Page<Store> stores = storeRepository.findAllSilverStar(pageable);
        return stores.map(store -> new StoreListingResponse(store));
    }

    public Page<StoreListingResponse> findAllSilverPriceASC(Pageable pageable) {
        Page<Store> stores = storeRepository.findAllSilverPriceASC(pageable);
        return stores.map(store -> new StoreListingResponse(store));
    }

    public Page<StoreListingResponse> findAllSilverPriceDESC(Pageable pageable) {
        Page<Store> stores = storeRepository.findAllSilverPriceDESC(pageable);
        return stores.map(store -> new StoreListingResponse(store));
    }
    public Page<StoreListingResponse> findAllGoldDate(Pageable pageable) {
        Page<Store> stores = storeRepository.findAllGoldDate(pageable);
        return stores.map(store -> new StoreListingResponse(store));
    }

    public Page<StoreListingResponse> findAllGoldStar(Pageable pageable) {
        Page<Store> stores = storeRepository.findAllGoldStar(pageable);
        return stores.map(store -> new StoreListingResponse(store));
    }

    public Page<StoreListingResponse> findAllGoldPriceASC(Pageable pageable) {
        Page<Store> stores = storeRepository.findAllGoldPriceASC(pageable);
        return stores.map(store -> new StoreListingResponse(store));
    }

    public Page<StoreListingResponse> findAllGoldPriceDESC(Pageable pageable) {
        Page<Store> stores = storeRepository.findAllGoldPriceDESC(pageable);
        return stores.map(store -> new StoreListingResponse(store));
    }
    public Store findById(Long storeId){
        return storeRepository.findById(storeId).get();
    }




}
