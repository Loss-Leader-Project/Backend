package lossleaderproject.back.store.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.minio.MinioService;
import lossleaderproject.back.store.dto.StoreListingResponse;
import lossleaderproject.back.store.dto.StoreRequest;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.store.repository.StoreFoodImageRepository;
import lossleaderproject.back.store.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;
    private final MinioService minioService;

    // userId로 반환
    public Store save(StoreRequest storeRequest) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String thumbnailImageIdentify = UUID.randomUUID().toString()+".jpg";
        storeRequest.setThumbnailImageIdentify(thumbnailImageIdentify);
        minioService.imageUpload(
                "store",
                thumbnailImageIdentify,
                storeRequest.getThumbnailImage().getInputStream());
        Store store = storeRequest.storeRequestToEntity();
        return  storeRepository.save(store);
    }

    public Page<StoreListingResponse> findAllSilverDate(Pageable pageable) {
        Page<Store> stores = storeRepository.findAllSilverDate(pageable);
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
