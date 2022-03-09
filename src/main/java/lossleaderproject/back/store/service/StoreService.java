package lossleaderproject.back.store.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.minio.MinioService;
import lossleaderproject.back.store.dto.StoreListingResponse;
import lossleaderproject.back.store.dto.StoreRequest;
import lossleaderproject.back.store.dto.StoreResponse;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.store.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;
    private final MinioService minioService;

    // userId로 반환
    public Store save(StoreRequest storeRequest) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String thumbnailImageIdentify = UUID.randomUUID().toString() + ".jpg";
        storeRequest.setThumbnailImageIdentify(thumbnailImageIdentify);
        minioService.imageUpload(
                "store",
                thumbnailImageIdentify,
                storeRequest.getStoreDetailRequest().getStoreFoodImageRequestList().get(0).getImage().getInputStream());
        Store store = storeRequest.storeRequestToEntity();
        return storeRepository.save(store);
    }
    public Page<StoreListingResponse> findAllListing(Pageable pageable,String filter, String tier, String sorting) {
        Page<Store> stores = null;
        tier = tier.toUpperCase();
        filter = filter.toUpperCase();
        sorting = sorting.toUpperCase();
        if(tier.equals("HOTPLACE")){
            stores = storeRepository.findTop20ByOrderByAvgStarDesc(pageable);
        }
        else if(tier.equals("SILVER") || tier.equals("GOLD")) {
            // 티어 별 리스팅
            if(filter.equals("PRICE")) {
                if (sorting.equals("DESC")) {
                    stores = storeRepository.findAllByCouponGradeNameOrderByPriceOfCouponDesc(tier,pageable);
                } else {
                    stores = storeRepository.findAllByCouponGradeNameOrderByPriceOfCouponAsc(tier,pageable);
                }
            }
            else if(filter.equals("DATE")) {
                stores = storeRepository.findAllByCouponGradeNameOrderByCreateDateAsc(tier,pageable);
            }
            else if(filter.equals("STAR")){
                stores = storeRepository.findAllByCouponGradeNameOrderByAvgStarDesc(tier,pageable);
            }
            return stores.map(store -> new StoreListingResponse(store));
        }
        else if(tier.equals("ALL")){
            // 전체 리스팅
            if(filter.equals("PRICE")) {
                if (sorting.equals("DESC")) {
                    stores = storeRepository.findAllByOrderByPriceOfCouponDesc(pageable);
                } else {
                    stores = storeRepository.findAllByOrderByPriceOfCoupon(pageable);
                }
            }
            else if(filter.equals("DATE")) {
                stores = storeRepository.findAllByOrderByCreateDateAsc(pageable);
            }
            else if(filter.equals("STAR")){
                stores = storeRepository.findAllByOrderByAvgStarDesc(pageable);
            }
            return stores.map(store -> new StoreListingResponse(store));
        }
        // 값 제대로 들어오지 않을 시에 default  프론트 분과 얘기 됐습니다.
        stores = storeRepository.findAllByOrderByPriceOfCouponDesc(pageable);
        return stores.map(store -> new StoreListingResponse(store));
    }

    public Store findById(Long storeId){
        return storeRepository.findById(storeId).get();
    }

    // 리뷰시 업체 평균 별점 변경 입니다. review service 에서 사용 합니다.
    public void review(Long storeId, Float star){
        Store store = storeRepository.findById(storeId).get();
        Integer newReviewCount = store.getReviewCount() + 1;
        Float newAvgStar = (store.getAvgStar() * store.getReviewCount() + star) / newReviewCount;
        store.setReviewCount(newReviewCount);
        store.setAvgStar(newAvgStar);
        storeRepository.save(store);
    }

    public List< StoreResponse.StoreHotplace> findTop20ByOrderByAvgStarDesc(){
        List<Store> stores = storeRepository.findTop20ByOrderByAvgStarDesc();
        List<StoreResponse.StoreHotplace> storesToListing = new ArrayList<>();
        for (Store store : stores) {
            storesToListing.add(new StoreResponse.StoreHotplace(store));
        }
        return storesToListing;
    }




}
