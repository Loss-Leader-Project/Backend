package lossleaderproject.back.order.service;

import lossleaderproject.back.order.dto.OrderRequest;
import lossleaderproject.back.user.dto.UserRequest;
import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest
class OrderServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    EntityManager em;


    @Test
    @DisplayName("정상적인 주문이 완료시 id가 1씩 증가합니다.")
    public void 주문() throws Exception {
        // given
        UserRequest userRequest = new UserRequest("test1", "test1!", "test1!", "test1", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", LocalDateTime.now(), null, 1000);
        User saveUser = userRepository.save(userRequest.toEntity());
        UserRequest userRequest2 = new UserRequest("hoe", "test1!", "test1!", "hoe", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", LocalDateTime.now(), null, 1000);
        User saveUser2 = userRepository.save(userRequest2.toEntity());
        OrderRequest orderRequest1 = new OrderRequest("test1", "01012341234", 20220213, LocalTime.now(), 3, 1000, 0, true, true);
        OrderRequest orderRequest2 = new OrderRequest("hoe", "01012341234", 20220214, LocalTime.now(), 3, 2000, 0, true, true);

        // when

        Long orderId1 = orderService.productOrder(orderRequest1);
        Long orderId2 = orderService.productOrder(orderRequest2);

        // then
        Assertions.assertThat(orderId1).isEqualTo(1);
        Assertions.assertThat(orderId2).isEqualTo(2);
    }

    @Test
    @DisplayName("마일리지 전액 사용이 True일 경우 사용자의 마일리지를 전부 사용하거나 주문금액에 맞게 사용되고 사용된 수 만큼 사용자의 마일리지는 차감됩니다.")
    @Transactional
    public void 전액사용_true() throws Exception {
        // given
        UserRequest userRequest = new UserRequest("test1", "test1!", "test1!", "test1", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", LocalDateTime.now(), null, 1000);
        UserRequest userRequest2 = new UserRequest("hoe", "test1!", "test1!", "hoe", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", LocalDateTime.now(), null, 1000);
        User saveUser = userRepository.save(userRequest.toEntity());
        User saveUser2 = userRepository.save(userRequest2.toEntity());

        OrderRequest orderRequest1 = new OrderRequest("test1", "01012341234", 20220213, LocalTime.now(), 3, 1000, 1000, true, true);
        OrderRequest orderRequest2 = new OrderRequest("hoe", "01012341234", 20220214, LocalTime.now(), 3, 2000, 2000, true, true);
        // when
        orderService.productOrder(orderRequest1);
        orderService.productOrder(orderRequest2);
        int userMileage1 = saveUser.getMileage();
        int userMileage2 = saveUser2.getMileage();
        System.out.println("saveUser.getMileage() = " + saveUser.getMileage());
        // then
        Assertions.assertThat(userMileage1).isEqualTo(2000);
        Assertions.assertThat(userMileage2).isEqualTo(1000);
    }





}