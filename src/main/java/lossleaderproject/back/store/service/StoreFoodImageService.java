package lossleaderproject.back.store.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.store.dto.StoreFoodImageRequest;
import lossleaderproject.back.store.dto.StoreFoodImageResponse;
import lossleaderproject.back.store.entitiy.StoreDetail;
import lossleaderproject.back.store.entitiy.StoreFoodImage;
import lossleaderproject.back.store.repository.StoreDetailRepository;
import lossleaderproject.back.store.repository.StoreFoodImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreFoodImageService {

    private final StoreDetailRepository storeDetailRepository;
    private final StoreFoodImageRepository storeFoodImageRepository;


    public  List<StoreFoodImageResponse> save(Long storeDetailId, List<StoreFoodImageRequest> storeFoodImageRequestList) {
        StoreDetail storeDetail = storeDetailRepository.findById(storeDetailId).get();
        List<StoreFoodImageResponse> storeFoodImageResponseList = new ArrayList<>();
          for (StoreFoodImageRequest storeFoodImageRequest : storeFoodImageRequestList) {
            StoreFoodImage storeFoodImage = storeFoodImageRequest.storeFoodImageRequestToEntity();
            storeFoodImage.setStoreDetail(storeDetail);
            storeFoodImageRepository.save(storeFoodImage);
            storeFoodImageResponseList.add(new StoreFoodImageResponse(storeFoodImage.getId(),storeFoodImage.getImage(),storeFoodImage.getName()));
        }
        return storeFoodImageResponseList;
    }

    public List<StoreFoodImageResponse> findOneByDetailId(Long storeDetailId) {
        List<StoreFoodImage> storeFoodImageList = storeFoodImageRepository.findOneByDetailId(storeDetailId);
        List<StoreFoodImageResponse> storeFoodImageResponseList = new ArrayList<>();
        for (StoreFoodImage storeFoodImage : storeFoodImageList) {
            storeFoodImageResponseList.add(new StoreFoodImageResponse(storeFoodImage.getId(),storeFoodImage.getImage(),storeFoodImage.getName()));
        }
        return storeFoodImageResponseList;
    }
}
