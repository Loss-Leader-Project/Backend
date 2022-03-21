package lossleaderproject.back.store.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
import springfox.documentation.annotations.ApiIgnore;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
@RestController
@RequiredArgsConstructor
@Api(tags = "업체에 대한 API")
@RequestMapping("/store")
public class StoreController {


    private final StoreService storeService;
    private final StoreDetailService storeDetailService;
    private final StoreFoodImageService storeFoodImageService;
    private final StoreHashTagService storeHashTagService;
    private final StoreMenuService storeMenuService;

    @ApiOperation(value = "업체 등록 (닫을 예정 관리자 페이지 생기면 다시)")
    @PostMapping("")
    public ResponseEntity<StoreResponse.StoreRes> storePost(StoreRequest storeRequest) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        Store store = storeService.save(storeRequest);
        StoreDetail storeDetail= storeDetailService.save(store.getId(),storeRequest.getStoreDetailRequest());
        StoreDetailResponse.StoreDetailPosting storeDetailResponse = new StoreDetailResponse.StoreDetailPosting(
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
        StoreResponse.StoreRes storeResponse = new StoreResponse.StoreRes(store,storeDetailResponse);
        return ResponseEntity.ok(storeResponse);
    }
    @ApiOperation(value = "업체 정보 상세보기 (업체 디테일 페이지)")
    @ApiImplicitParam(name = "storeId", value = "업체ID")
    @GetMapping("/detail")
    public ResponseEntity<StoreResponse.StoreDetailPageRes> getDetail(@RequestParam("storeId") Long storeId) {
        Store store= storeService.findById(storeId);
        StoreDetail storeDetail= storeDetailService.findByStoreId(storeId);
        Long storeDetailId = storeDetail.getId();

        List<StoreFoodImageResponse> storeFoodImageResponseList = storeFoodImageService.findAllByStoreDetailId(storeDetailId);
        List<StoreMenuResponse> storeMenuResponseList = storeMenuService.findAllByStoreDetailId(storeDetailId);
        List<StoreHashTagResponse> storeHashTagResponseList = storeHashTagService.findAllByStoreDetailId(storeDetailId);
        StoreResponse.StoreTopData storeTopData = new StoreResponse.StoreTopData(store,storeFoodImageResponseList,storeHashTagResponseList);
        StoreDetailResponse.StoreDetailForDetailPage storeDetailResponse = new StoreDetailResponse.StoreDetailForDetailPage(
                storeDetail.getStorePhoneNumber(),
                storeDetail.getOperatingPeriod(),
                storeDetail.getRoadAddress(),
                storeDetail.getOperatingTime(),
                store.getContent(),
                store.getStoreMeal(),
                store.getPackaging(),
                store.getDelivery(),
                storeDetail.getLatitude(),
                storeDetail.getLongitude(),
                storeDetail.getStoreMenuImage(),
                storeMenuResponseList
        );
        StoreResponse.StoreDetailPageRes storeDetailPageRes = new StoreResponse.StoreDetailPageRes(storeId,storeTopData,storeDetailResponse);
        return ResponseEntity.ok(storeDetailPageRes);
    }

    @ApiOperation(value = "업체 리스팅 (업체 리스팅 페이지)", notes = "쿠폰 등급별로 정렬(실버, 골드)")
    @GetMapping("/list")
    public ResponseEntity<Page<StoreListingResponse>> storeListing (@RequestParam(value = "filter",required = false,defaultValue = "PRICE") String filter,
                                                                    @RequestParam(value = "tier",required = false,defaultValue = "ALL") String tier,
                                                                    @RequestParam(value = "sorting",required = false,defaultValue = "DESC") String sorting,
                                                                   @ApiIgnore @PageableDefault(page = 0, size = 20) Pageable pageable)

    {
        return ResponseEntity.ok(storeService.findAllListing(pageable,filter,tier,sorting));
    }


    @ApiOperation(value = "핫플레이스 (메인 페이지 핫 플레이스)")
    @GetMapping("/listing-hot")
    public ResponseEntity<List<StoreResponse.StoreHotplace>> findAllHotPlace() {
        return ResponseEntity.ok(storeService.findTop20ByOrderByAvgStarDesc());
    }
}
