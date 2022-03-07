package lossleaderproject.back.store.controller;


import com.sun.istack.Nullable;
import lombok.RequiredArgsConstructor;
import lossleaderproject.back.store.dto.*;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.store.entitiy.StoreDetail;
import lossleaderproject.back.store.service.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {


    private final StoreService storeService;
    private final StoreDetailService storeDetailService;
    private final StoreFoodImageService storeFoodImageService;
    private final StoreHashTagService storeHashTagService;
    private final StoreMenuService storeMenuService;

    @PostMapping()
    public ResponseEntity<StoreResponse> newMember(StoreRequest storeRequest) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        Store store = storeService.save(storeRequest);
        StoreDetail storeDetail= storeDetailService.save(store.getId(),storeRequest.getStoreDetailRequest());
        StoreDetailResponse storeDetailResponse = new StoreDetailResponse(
                storeDetail.getId(),
                storeDetail.getStorePhoneNumber(),
                storeDetail.getOperatingTime(),
                storeDetail.getOperatingPeriod(),
                storeDetail.getRoadAddress(),
                storeDetail.getLatitude(),
                storeDetail.getLongitude(),
                storeDetail.getStoreMenuImage(),
                storeFoodImageService.save(storeDetail.getId(),storeRequest.getStoreDetailRequest().getStoreFoodImageRequestList()),
                storeMenuService.save(storeDetail.getId(),storeRequest.getStoreDetailRequest().getStoreMenuRequestList()),
                storeHashTagService.save(storeDetail.getId(),storeRequest.getStoreDetailRequest().getStoreHashTagRequestList())
        );
        StoreResponse storeResponse = new StoreResponse(store,storeDetailResponse);
        return ResponseEntity.ok(storeResponse);
    }
    @GetMapping("/detail")
    public ResponseEntity<StoreResponse> test(@RequestParam("storeId") Long storeId) {
        Store store= storeService.findById(storeId);
        StoreDetail storeDetail= storeDetailService.findByStoreId(storeId);
        Long storeDetailId = storeDetail.getId();
    
        List<StoreFoodImageResponse> storeFoodImageResponseList = storeFoodImageService.findAllByStoreDetailId(storeDetailId);
        List<StoreMenuResponse> storeMenuResponseList = storeMenuService.findAllByStoreDetailId(storeDetailId);
        List<StoreHashTagResponse> storeHashTagResponseList = storeHashTagService.findAllByStoreDetailId(storeDetailId);
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

        //storeDetailResponse.setStoreFoodImageResponseList(storeFoodImageService.findOneByDetailId(storeDetailId));
        return ResponseEntity.ok(storeResponse);
    }


    @GetMapping("list/")
    public ResponseEntity<Page<StoreListingResponse>> findAllSilverPrice(@RequestParam(value = "filter",required = false,defaultValue = "PRICE") String filter,
                                                                         @RequestParam(value = "tier",required = false,defaultValue = "ALL") String tier,
                                                                         @RequestParam(value = "sorting",required = false,defaultValue = "DESC") String sorting,
                                                                         @PageableDefault(page = 0, size = 20) Pageable pageable)
    {
        return ResponseEntity.ok(storeService.findAllListing(pageable,filter,tier,sorting));
    }
}
