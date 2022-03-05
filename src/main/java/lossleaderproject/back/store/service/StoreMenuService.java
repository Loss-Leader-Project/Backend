package lossleaderproject.back.store.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.store.dto.StoreFoodImageResponse;
import lossleaderproject.back.store.dto.StoreMenuRequest;
import lossleaderproject.back.store.dto.StoreMenuResponse;
import lossleaderproject.back.store.entitiy.StoreDetail;
import lossleaderproject.back.store.entitiy.StoreFoodImage;
import lossleaderproject.back.store.entitiy.StoreMenu;
import lossleaderproject.back.store.repository.StoreDetailRepository;
import lossleaderproject.back.store.repository.StoreMenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreMenuService {
    private final StoreDetailRepository storeDetailRepository;
    private final StoreMenuRepository storeMenuRepository;

    public List<StoreMenuResponse> save(Long storeDetailId, List<StoreMenuRequest> storeMenuRequestList) {
        StoreDetail storeDetail = storeDetailRepository.findById(storeDetailId).get();
        List<StoreMenuResponse> storeMenuResponseList = new ArrayList<>();
        for (StoreMenuRequest storeMenuRequest : storeMenuRequestList) {
            StoreMenu storeMenu = storeMenuRequest.storeMenuRequestToEntity();
            storeMenu.setStoreDetail(storeDetail);
            storeMenuRepository.save(storeMenu);
            storeMenuResponseList.add(new StoreMenuResponse(storeMenu.getId(),storeMenu.getName(),storeMenu.getPrice()));
        }
        return storeMenuResponseList;
    }


    public List<StoreMenuResponse> findAllByStoreDetailId(Long storeDetailId) {
        List<StoreMenu> storeMenuList = storeMenuRepository.findAllByStoreDetailId(storeDetailId);
        List<StoreMenuResponse> storeMenuResponseList = new ArrayList<>();
        for (StoreMenu storeMenu : storeMenuList) {
            storeMenuResponseList.add(new StoreMenuResponse(storeMenu.getId(),storeMenu.getName(),storeMenu.getPrice()));
        }
        return storeMenuResponseList;
    }
}
