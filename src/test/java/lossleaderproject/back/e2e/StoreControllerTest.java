package lossleaderproject.back.e2e;



import com.nimbusds.jose.shaded.json.parser.ParseException;
import lossleaderproject.back.store.dto.StoreResponse;
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

        StoreFoodImage storeFoodImage = new StoreFoodImage("식별자", "이미지 이름");
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
    @DisplayName("업체 상세 정보 확인 정상 테스트")
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
            JSONObject responseBodyObject = new JSONObject(new String(response.returnResult(ResponseEntity.class).getResponseBodyContent(), "UTF-8"));

            // 상태 코드 확인
            Assertions.assertThat(responseStatus).isEqualTo(HttpStatus.OK);

            // Response Body 확인 시작
            Assertions.assertThat(responseBodyObject.getLong("id")).isEqualTo((store.getId()));

            // StoreTopData DTO
            Assertions.assertThat(responseBodyObject.getJSONObject("storeTopData").getString("briefAddress")).isEqualTo(store.getBriefAddress());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeTopData").getString("storeName")).isEqualTo(store.getStoreName());
            Assertions.assertThat(Float.valueOf(responseBodyObject.getJSONObject("storeTopData").getString("avgStar"))).isEqualTo(store.getAvgStar()); // Float 으로 받는게 없어서 형변환 직접 했습니다.
            Assertions.assertThat(responseBodyObject.getJSONObject("storeTopData").getInt("priceOfCoupon")).isEqualTo(store.getPriceOfCoupon());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeTopData").getString("benefitCondition")).isEqualTo(store.getBenefitCondition());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeTopData").getString("couponContent")).isEqualTo(store.getCouponContent());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeTopData").getInt("leftCoupon")).isEqualTo(store.getLeftCoupon());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeTopData").getInt("totalCoupon")).isEqualTo(store.getTotalCoupon());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeTopData").getString("couponGradeName")).isEqualTo(store.getCouponGradeName());

            // Store Food Image 확인
            Assertions.assertThat(responseBodyObject.getJSONObject("storeTopData").getJSONArray("storeFoodImageResponseList").getJSONObject(0).getLong("id")).isEqualTo(storeFoodImages.get(0).getId());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeTopData").getJSONArray("storeFoodImageResponseList").getJSONObject(0).getString("image")).isEqualTo(storeFoodImages.get(0).getImage());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeTopData").getJSONArray("storeFoodImageResponseList").getJSONObject(0).getString("name")).isEqualTo(storeFoodImages.get(0).getName());

            // Store Hash Tag 확인
            Assertions.assertThat(responseBodyObject.getJSONObject("storeTopData").getJSONArray("storeHashTagResponseList").getJSONObject(0).getLong("id")).isEqualTo(storeHashTags.get(0).getId());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeTopData").getJSONArray("storeHashTagResponseList").getJSONObject(0).getString("name")).isEqualTo(storeHashTags.get(0).getName());

            // StoreDetailForDetailPage DTO
            Assertions.assertThat(responseBodyObject.getJSONObject("storeDetailResponse").getString("storePhoneNumber")).isEqualTo(storeDetail.getStorePhoneNumber());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeDetailResponse").getString("operatingPeriod")).isEqualTo(storeDetail.getOperatingPeriod());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeDetailResponse").getString("roadAddress")).isEqualTo(storeDetail.getRoadAddress());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeDetailResponse").getString("operatingTime")).isEqualTo(storeDetail.getOperatingTime());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeDetailResponse").getString("content")).isEqualTo(store.getContent());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeDetailResponse").getBoolean("storeMeal")).isEqualTo(store.getStoreMeal());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeDetailResponse").getBoolean("packaging")).isEqualTo(store.getPackaging());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeDetailResponse").getBoolean("delivery")).isEqualTo(store.getDelivery());
            Assertions.assertThat(Float.valueOf(responseBodyObject.getJSONObject("storeDetailResponse").getString("latitude"))).isEqualTo(storeDetail.getLatitude());
            Assertions.assertThat(Float.valueOf(responseBodyObject.getJSONObject("storeDetailResponse").getString("longitude"))).isEqualTo(storeDetail.getLongitude());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeDetailResponse").getString("storeMenuImage")).isEqualTo(storeDetail.getStoreMenuImage());

            // Store Menu 확인
            Assertions.assertThat(responseBodyObject.getJSONObject("storeDetailResponse").getJSONArray("storeMenuResponseList").getJSONObject(0).getLong("id")).isEqualTo(storeMenus.get(0).getId());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeDetailResponse").getJSONArray("storeMenuResponseList").getJSONObject(0).getString("name")).isEqualTo(storeMenus.get(0).getName());
            Assertions.assertThat(responseBodyObject.getJSONObject("storeDetailResponse").getJSONArray("storeMenuResponseList").getJSONObject(0).getInt("price")).isEqualTo(storeMenus.get(0).getPrice());

        // to initialize
            storeDeleteAll();
    }

    @Test
    @DisplayName("업체 상세 정보 확인 비 정상 테스트")
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
