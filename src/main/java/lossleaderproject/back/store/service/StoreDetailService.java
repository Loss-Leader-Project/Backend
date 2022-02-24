package lossleaderproject.back.store.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.store.dto.StoreDetailRequest;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.store.entitiy.StoreDetail;
import lossleaderproject.back.store.repository.StoreDetailRepository;
import lossleaderproject.back.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class StoreDetailService {

    private final StoreRepository storeRepository;
    private final StoreDetailRepository storeDetailRepository;

    public StoreDetail save(Long storeId, StoreDetailRequest storeDetailRequest) {
        Store store = storeRepository.findById(storeId).get();
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
