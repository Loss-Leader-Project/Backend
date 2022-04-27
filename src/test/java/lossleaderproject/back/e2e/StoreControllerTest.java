package lossleaderproject.back.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import lossleaderproject.back.store.dto.*;
import lossleaderproject.back.store.entitiy.*;
import lossleaderproject.back.store.repository.*;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.io.*;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class StoreControllerTest {

    @Autowired
    StoreRepository storeRepository;
    @Autowired
    StoreDetailRepository storeDetailRepository;
    @Autowired
    StoreFoodImageRepository storeFoodImageRepository;
    @Autowired
    StoreMenuRepository storeMenuRepository;
    @Autowired
    StoreHashTagRepository storeHashTagRepository;
    @Autowired
    WebTestClient webTestClient;

    Long storePost() {
        Store store = storeRepository.save(new Store("성수동", "음식점 이름", "이미지 url", "가능한 서비스 방법들", Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, 1000, "~ 구매시", "10% 할인", 10, 10, "gold"));

        StoreDetail storeDetail = new StoreDetail("업체 전화 번호", "09:00 ~ 21:00", "연중 무휴", "도로명 주소", 123.123f, 123.123f, "이미지 url");

        storeDetail.setStoreDetailInStore(store);
        storeDetailRepository.save(storeDetail);

        StoreFoodImage storeFoodImage = new StoreFoodImage("이미지 식별자", "이미지 이름");
        storeFoodImage.setStoreDetail(storeDetail);
        storeFoodImageRepository.save(storeFoodImage);

        StoreMenu storeMenu = new StoreMenu("메뉴 이름", 1000);
        storeMenu.setStoreDetail(storeDetail);
        storeMenuRepository.save(storeMenu);

        StoreHashTag storeHashTag = new StoreHashTag("해시태그");
        storeHashTag.setStoreDetail(storeDetail);
        storeHashTagRepository.save(storeHashTag);

        return store.getId();
    }

    void storeDeleteAll() {
        storeHashTagRepository.deleteAll();
        storeMenuRepository.deleteAll();
        storeFoodImageRepository.deleteAll();
        storeDetailRepository.deleteAll();
        storeRepository.deleteAll();
    }




    @Test
    @DisplayName("storeId와 일치하는 store가 존재할 때 /store/detail 에서 200의 상태 코드와 store-detail 페이지의 정보를 정상적으로 조회한다.")
    void storeDetailPossibleTest() throws IOException, ClassNotFoundException, ParseException, JSONException {
        // given
            Long storeId = storePost();

        // when
            var response = webTestClient.get().uri(uriBuilder -> uriBuilder.path("/store/detail").queryParam("storeId", storeId).build()).accept(MediaType.APPLICATION_JSON_UTF8).exchange();



        // then

            // default로 처리해주는 값도 있어서 repository에서 직점 꺼냈습니다.
            Store store = storeRepository.findById(storeId).get();
            StoreDetail storeDetail = storeDetailRepository.findByStoreId(storeId);
            List<StoreFoodImage> storeFoodImages = storeFoodImageRepository.findAllByStoreDetailId(storeDetail.getId());
            List<StoreHashTag> storeHashTags = storeHashTagRepository.findAllByStoreDetailId(storeDetail.getId());
            List<StoreMenu> storeMenus = storeMenuRepository.findAllByStoreDetailId(storeDetail.getId());



            // 상태코드와 결과값(Json Object) 받음
            HttpStatus responseStatus = response.returnResult(ResponseEntity.class).getStatus();

            ObjectMapper objectMapper = new ObjectMapper();
            StoreResponse.StoreDetailPageRes storeDetailPageRes = objectMapper.readValue(new String(response.returnResult(ResponseEntity.class).getResponseBodyContent(),"UTF-8"),StoreResponse.StoreDetailPageRes.class);

            // 상태 코드 확인
            Assertions.assertThat(responseStatus).isEqualTo(HttpStatus.OK);

            // Response Body 확인 시작
            Assertions.assertThat(storeDetailPageRes.getId()).isEqualTo((store.getId()));

            // StoreTopData DTO
            StoreResponse.StoreTopData storeTopDataRes = storeDetailPageRes.getStoreTopData();

            Assertions.assertThat(storeTopDataRes.getBriefAddress()).isEqualTo(store.getBriefAddress());
            Assertions.assertThat(storeTopDataRes.getStoreName()).isEqualTo(store.getStoreName());
            Assertions.assertThat(storeTopDataRes.getAvgStar()).isEqualTo(store.getAvgStar()); // Float 으로 받는게 없어서 형변환 직접 했습니다.
            Assertions.assertThat(storeTopDataRes.getPriceOfCoupon()).isEqualTo(store.getPriceOfCoupon());
            Assertions.assertThat(storeTopDataRes.getBenefitCondition()).isEqualTo(store.getBenefitCondition());
            Assertions.assertThat(storeTopDataRes.getCouponContent()).isEqualTo(store.getCouponContent());
            Assertions.assertThat(storeTopDataRes.getLeftCoupon()).isEqualTo(store.getLeftCoupon());
            Assertions.assertThat(storeTopDataRes.getTotalCoupon()).isEqualTo(store.getTotalCoupon());
            Assertions.assertThat(storeTopDataRes.getCouponGradeName()).isEqualTo(store.getCouponGradeName());

            // Store Food Image 확인
            List<StoreFoodImageResponse> storeFoodImagesRes = storeTopDataRes.getStoreFoodImageResponseList();

            Assertions.assertThat(storeFoodImagesRes.get(0).getId()).isEqualTo(storeFoodImages.get(0).getId());
            Assertions.assertThat(storeFoodImagesRes.get(0).getImage()).isEqualTo(storeFoodImages.get(0).getImage());
            Assertions.assertThat(storeFoodImagesRes.get(0).getName()).isEqualTo(storeFoodImages.get(0).getName());

            // Store Hash Tag 확인
            List<StoreHashTagResponse> storeHashTagsRes = storeTopDataRes.getStoreHashTagResponseList();

            Assertions.assertThat(storeHashTagsRes.get(0).getId()).isEqualTo(storeHashTags.get(0).getId());
            Assertions.assertThat(storeHashTagsRes.get(0).getName()).isEqualTo(storeHashTags.get(0).getName());

            // storeDetailResponse DTO
            StoreDetailResponse.StoreDetailForDetailPage storeDetailRes = storeDetailPageRes.getStoreDetailResponse();

            Assertions.assertThat(storeDetailRes.getStorePhoneNumber()).isEqualTo(storeDetail.getStorePhoneNumber());
            Assertions.assertThat(storeDetailRes.getOperatingPeriod()).isEqualTo(storeDetail.getOperatingPeriod());
            Assertions.assertThat(storeDetailRes.getRoadAddress()).isEqualTo(storeDetail.getRoadAddress());
            Assertions.assertThat(storeDetailRes.getOperatingTime()).isEqualTo(storeDetail.getOperatingTime());
            Assertions.assertThat(storeDetailRes.getContent()).isEqualTo(store.getContent());
            Assertions.assertThat(storeDetailRes.getStoreMeal()).isEqualTo(store.getStoreMeal());
            Assertions.assertThat(storeDetailRes.getPackaging()).isEqualTo(store.getPackaging());
            Assertions.assertThat(storeDetailRes.getDelivery()).isEqualTo(store.getDelivery());
            Assertions.assertThat(storeDetailRes.getLatitude()).isEqualTo(storeDetail.getLatitude());
            Assertions.assertThat(storeDetailRes.getLongitude()).isEqualTo(storeDetail.getLongitude());
            Assertions.assertThat(storeDetailRes.getStoreMenuImage()).isEqualTo(storeDetail.getStoreMenuImage());

            // Store Menu 확인
            List<StoreMenuResponse> storeMenusRes = storeDetailRes.getStoreMenuResponseList();

            Assertions.assertThat(storeMenusRes.get(0).getId()).isEqualTo(storeMenus.get(0).getId());
            Assertions.assertThat(storeMenusRes.get(0).getName()).isEqualTo(storeMenus.get(0).getName());
            Assertions.assertThat(storeMenusRes.get(0).getPrice()).isEqualTo(storeMenus.get(0).getPrice());

        // to initialize
            storeDeleteAll();
    }

    @Test
    @DisplayName("storeId와 일치하는 store가 존재하지 않을 때 /store/detail 에서 500 상태 코드(SERVER_ERROR) 를 반환한다.")
    void storeDetailImpossibleTest() throws IOException, ClassNotFoundException, ParseException, JSONException {
        // given
            storeDeleteAll();

        // when
            var response = webTestClient.get().uri(uriBuilder -> uriBuilder.path("/store/detail").queryParam("storeId", 1L).build()).accept(MediaType.APPLICATION_JSON_UTF8).exchange();

        // then
            // 상태코드와 결과값(Json Object) 받음
            HttpStatus responseStatus = response.returnResult(ResponseEntity.class).getStatus();
            JSONObject responseBodyObject = new JSONObject(new String(response.returnResult(ResponseEntity.class).getResponseBodyContent(), "UTF-8"));

        // 상태 코드 확인
            Assertions.assertThat(responseStatus).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
