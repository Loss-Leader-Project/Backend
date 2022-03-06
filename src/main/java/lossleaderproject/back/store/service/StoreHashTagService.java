package lossleaderproject.back.store.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.store.dto.StoreHashTagRequest;
import lossleaderproject.back.store.dto.StoreHashTagResponse;
import lossleaderproject.back.store.entitiy.StoreDetail;
import lossleaderproject.back.store.entitiy.StoreHashTag;
import lossleaderproject.back.store.repository.StoreDetailRepository;
import lossleaderproject.back.store.repository.StoreHashTagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreHashTagService {
    private final StoreDetailRepository storeDetailRepository;
    private final StoreHashTagRepository storeHashTagRepository;

    public List<StoreHashTagResponse> save(Long storeDetailId, List<StoreHashTagRequest> storeHashTagRequestList) {
        StoreDetail storeDetail = storeDetailRepository.findById(storeDetailId).get();
        List<StoreHashTagResponse> storeHashTagResponseList = new ArrayList<>();
        for (StoreHashTagRequest storeHashTagRequest : storeHashTagRequestList) {
            StoreHashTag storeHashTag = storeHashTagRequest.storeHashTagRequestToEntity();
            storeHashTag.setStoreDetail(storeDetail);
            storeHashTagRepository.save(storeHashTag);
            storeHashTagResponseList.add(new StoreHashTagResponse(storeHashTag.getId(),storeHashTag.getName()));
        }
        return storeHashTagResponseList;
    }

    public List<StoreHashTagResponse> findAllByStoreDetailId(Long storeDetailId) {
        List<StoreHashTag> storeHashTagList = storeHashTagRepository.findAllByStoreDetailId(storeDetailId);
        List<StoreHashTagResponse> storeHashTagResponseList = new ArrayList<>();
        for (StoreHashTag storeHashTag : storeHashTagList) {
            storeHashTagResponseList.add(new StoreHashTagResponse(storeHashTag.getId(),storeHashTag.getName()));
        }
        return storeHashTagResponseList;
    }
}
