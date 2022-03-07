package lossleaderproject.back.order.service;

import lossleaderproject.back.store.service.StoreService;
import lossleaderproject.back.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

@SpringBootTest
class OrderServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    EntityManager em;
    @Autowired
    StoreService storeService;


  /*  @Test
    @DisplayName("정상적인 주문이 완료시 id가 1씩 증가합니다.")
    @Transactional
    public void 주문() throws Exception {
        // given
        UserRequest userRequest = new UserRequest("test1", "test1!", "test1!", "test1", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", "9810151", null,123123);
        User saveUser = userRepository.save(userRequest.toEntity());
        UserRequest userRequest2 = new UserRequest("hoe", "test1!", "test1!", "hoe", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", "9705021", null, 31323);
        User saveUser2 = userRepository.save(userRequest2.toEntity());
        OrderRequest orderRequest1 = new OrderRequest("test1", "01012341234", 20220213, LocalTime.now(), 3,  0, true, true);
        OrderRequest orderRequest2 = new OrderRequest("hoe", "01012341234", 20220214, LocalTime.now(), 3,  0, true, true);
        StoreRequest storeRequest1 = new StoreRequest("송도점", "우우우", "www.http", "매장식사",true,true,true, 3, null, 2000, "할인조건", "쿠폰설명", 5, 5, 1);
        StoreRequest storeRequest2 = new StoreRequest("상동점", "우우우", "www.http", serviceMethod2, 4, null, 2000, "할인조건", "쿠폰설명", 5, 5, 1);
        // when
        storeService.save(storeRequest1);
        storeService.save(storeRequest2);

        Long orderId1 = orderService.productOrder(1L, orderRequest1);
        Long orderId2 = orderService.productOrder(2L, orderRequest2);

        // then
        Assertions.assertThat(orderId1).isEqualTo(1);
        Assertions.assertThat(orderId2).isEqualTo(2);
    }

    @Test
    @DisplayName("마일리지 전액 사용이 True일 경우 사용자의 마일리지를 전부 사용하거나 주문금액에 맞게 사용되고 사용된 수 만큼 사용자의 마일리지는 차감됩니다.")
    @Transactional
    public void 전액사용_true() throws Exception {
        // given
        UserRequest userRequest = new UserRequest("test1", "test1!", "test1!", "test1", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", "9810151", null, 1000);
        UserRequest userRequest2 = new UserRequest("hoe", "test1!", "test1!", "hoe", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", "1011301", null, 1000);
        User saveUser = userRepository.save(userRequest.toEntity());
        User saveUser2 = userRepository.save(userRequest2.toEntity());

        OrderRequest orderRequest1 = new OrderRequest("test1", "01012341234", 20220213, LocalTime.now(), 3,  1000, true, true);
        OrderRequest orderRequest2 = new OrderRequest("hoe", "01012341234", 20220214, LocalTime.now(), 3,  2000, true, true);
        ServiceMethod serviceMethod1 = new ServiceMethod(true,false,false);
        ServiceMethod serviceMethod2 = new ServiceMethod(true,true,false);
        StoreRequest storeRequest1 = new StoreRequest("송도점", "우우우", "www.http", serviceMethod1, 3, null, 2000, "할인조건", "쿠폰설명", 5, 5, 1);
        StoreRequest storeRequest2 = new StoreRequest("상동점", "우우우", "www.http", serviceMethod2, 4, null, 1000, "할인조건", "쿠폰설명", 5, 5, 1);
        // when
        storeService.save(storeRequest1);
        storeService.save(storeRequest2);
        orderService.productOrder(1L, orderRequest1);
        orderService.productOrder(2L, orderRequest2);
        int userMileage1 = saveUser.getMileage();
        int userMileage2 = saveUser2.getMileage();
        System.out.println("saveUser.getMileage() = " + saveUser.getMileage());
        // then
        Assertions.assertThat(userMileage1).isEqualTo(1000);
        Assertions.assertThat(userMileage2).isEqualTo(2000);
    }

*/
}