package lossleaderproject.back.store.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.minio.MinioService;
import lossleaderproject.back.store.dto.StoreFoodImageRequest;
import lossleaderproject.back.store.dto.StoreFoodImageResponse;
import lossleaderproject.back.store.entitiy.StoreDetail;
import lossleaderproject.back.store.entitiy.StoreFoodImage;
import lossleaderproject.back.store.repository.StoreDetailRepository;
import lossleaderproject.back.store.repository.StoreFoodImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreFoodImageService {

    private final StoreDetailRepository storeDetailRepository;
    private final StoreFoodImageRepository storeFoodImageRepository;
    private final MinioService minioService;

    public  List<StoreFoodImageResponse> save(Long storeDetailId, List<StoreFoodImageRequest> storeFoodImageRequestList) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        StoreDetail storeDetail = storeDetailRepository.findById(storeDetailId).get();
        List<StoreFoodImageResponse> storeFoodImageResponseList = new ArrayList<>();
          for (StoreFoodImageRequest storeFoodImageRequest : storeFoodImageRequestList) {
              String imageIdentify = UUID.randomUUID().toString()+".jpg";
              storeFoodImageRequest.setImageIdentify(imageIdentify);
              minioService.imageUpload(
                      "lossleader",
                      imageIdentify,
                      storeFoodImageRequest.getImage().getInputStream());
            StoreFoodImage storeFoodImage = storeFoodImageRequest.storeFoodImageRequestToEntity();
            storeFoodImage.setStoreDetail(storeDetail);
            storeFoodImageRepository.save(storeFoodImage);
            storeFoodImageResponseList.add(new StoreFoodImageResponse(storeFoodImage.getId(),storeFoodImage.getImage(),storeFoodImage.getName()));
        }
        return storeFoodImageResponseList;
    }

    public List<StoreFoodImageResponse> findAllByStoreDetailId(Long storeDetailId) {
        System.out.println("----------------------------");
        System.out.println("storeDetailId = " + storeDetailId);
        List<StoreFoodImage> storeFoodImageList = storeFoodImageRepository.findAllByStoreDetailId(storeDetailId);
        List<StoreFoodImageResponse> storeFoodImageResponseList = new ArrayList<>();
        for (StoreFoodImage storeFoodImage : storeFoodImageList) {
            storeFoodImageResponseList.add(new StoreFoodImageResponse(storeFoodImage.getId(),storeFoodImage.getImage(),storeFoodImage.getName()));
        }
        return storeFoodImageResponseList;
    }
}
