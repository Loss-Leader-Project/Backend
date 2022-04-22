package lossleaderproject.back.store.controller;

import lossleaderproject.back.store.entitiy.*;
import lossleaderproject.back.store.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

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

    @Test
    void storePost() {
        // given
        Store store = storeRepository.save(new Store("성수동", "음식점 이름", "이미지 url", "가능한 서비스 방법들", Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, 1000, "~ 구매시", "10% 할인", 10, 10, "gold"));


        StoreDetail storeDetail = new StoreDetail("업체 전화 번호", "09:00 ~ 21:00", "연중 무휴", "도로명 주소", 123.123f, 123.123f, "이미지 url");
        storeDetail.setStoreDetailInStore(store);
        storeDetailRepository.save(storeDetail);

        StoreFoodImage storeFoodImage = new StoreFoodImage("이미지 url", "이미지 이름");
        storeFoodImage.setStoreDetail(storeDetail);
        storeFoodImageRepository.save(storeFoodImage);

        StoreMenu storeMenu = new StoreMenu("메뉴 이름", 1000);
        storeMenu.setStoreDetail(storeDetail);
        storeMenuRepository.save(storeMenu);

        StoreHashTag storeHashTag = new StoreHashTag("해시태그");
        storeHashTag.setStoreDetail(storeDetail);
        storeHashTagRepository.save(storeHashTag);


        // when
        var response = webTestClient.get().uri(uriBuilder -> uriBuilder.path("/store/detail").queryParam("storeId", store.getId()).build()).exchange();
//                .expectStatus()
//                .isOk()
//                .expectBody(String.class)
//                .returnResult()
//                .getResponseBody();

        // then
        var result = response.returnResult(ResponseEntity.class);
        Assertions.assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
    }
}
