package lossleaderproject.back.store.controller;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.store.dto.*;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.store.entitiy.StoreDetail;
import lossleaderproject.back.store.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store-detail")
public class StoreDetailController {
    private final StoreService storeService;
    private final StoreDetailService storeDetailService;
    private final StoreFoodImageService storeFoodImageService;
    private final StoreHashTagService storeHashTagService;
    private final StoreMenuService storeMenuService;

    @GetMapping()
    public ResponseEntity<StoreResponse> test(@RequestParam("storeId") Long storeId) {
        Store store= storeService.findById(storeId);
        StoreDetail storeDetail= storeDetailService.findByStoreId(storeId);
        Long storeDetailId = storeDetail.getId();

        List<StoreFoodImageResponse> storeFoodImageResponseList = storeFoodImageService.findAllByStoreDetailId(storeDetailId);
        List<StoreMenuResponse> storeMenuResponseList = storeMenuService.findOneByDetailId(storeDetailId);
        List<StoreHashTagResponse> storeHashTagResponseList = storeHashTagService.findOneByDetailId(storeDetailId);
        StoreDetailResponse storeDetailResponse = new StoreDetailResponse(
                storeDetail.getId(),
                storeDetail.getStorePhoneNumber(),
                storeDetail.getOperatingTime(),
                storeDetail.getOperatingPeriod(),
                storeDetail.getRoadAddress(),
                storeDetail.getLatitude(),
                storeDetail.getLongitude(),
                storeDetail.getStoreMenuImage(),
                storeFoodImageResponseList,
                storeMenuResponseList,
                storeHashTagResponseList
        );
        StoreResponse storeResponse = new StoreResponse(store,storeDetailResponse);

        return ResponseEntity.ok(storeResponse);
    }
}