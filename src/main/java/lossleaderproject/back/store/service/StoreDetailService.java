package lossleaderproject.back.store.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.minio.MinioService;
import lossleaderproject.back.store.dto.StoreDetailRequest;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.store.entitiy.StoreDetail;
import lossleaderproject.back.store.repository.StoreDetailRepository;
import lossleaderproject.back.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class StoreDetailService {

    private final StoreRepository storeRepository;
    private final StoreDetailRepository storeDetailRepository;
    private final MinioService minioService;

    public StoreDetail save(Long storeId, StoreDetailRequest storeDetailRequest) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        Store store = storeRepository.findById(storeId).get();
        String storeMenuImageIdentify = UUID.randomUUID().toString()+".jpg";
        storeDetailRequest.setStoreMenuImageIdentify(storeMenuImageIdentify);
        minioService.imageUpload(
                "lossleader",
                storeMenuImageIdentify,
                storeDetailRequest.getStoreMenuImage().getInputStream());
        StoreDetail storedetail = storeDetailRequest.storeDetailRequestToEntity();
        storedetail.setStoreDetailInStore(store);
        storeDetailRepository.save(storedetail);
        return storedetail;
    }

    public StoreDetail findByStoreId(Long storeId){
        StoreDetail storeDetail = storeDetailRepository.findByStoreId(storeId);
        return storeDetail;
    }

}
