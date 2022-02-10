package lossleaderproject.back.user.service;

import lossleaderproject.back.user.dto.UserRequest;
import lossleaderproject.back.user.entity.Address;
import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("사용자가 회원가입할때마다 i가 1씩 증가한다.") // 의미 없음
    // 생성된 id는 1씩 증가한다.
    public void 회원가입() throws Exception {
        // given
        Address address = new Address("1234-12", "어딘가", "자세히");
        UserRequest userRequest = new UserRequest("test1", "test1!", "test1!", "테스터", "01012341234", "abc@naver.com", address, LocalDateTime.now(), null, 1000);

        // when
        User saveUser = userRepository.save(userRequest.toEntity());

        // then
        Assertions.assertThat(saveUser.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("회원가입시 마일리지 3000 마일리지가 충전됩니다.")
    public void 회원가입_마일리지() throws Exception {
        // given
        Address address = new Address("1234-12", "어딘가", "자세히");
        UserRequest userRequest = new UserRequest("test1", "test1!", "test1!", "테스터", "01012341234", "abc@naver.com", address, LocalDateTime.now(), null, 1000);


        // when
        User savedUser = userRepository.save(userRequest.toEntity());
        // then
        Assertions.assertThat(savedUser.getMileage()).isEqualTo(3000);

    }

    @Test
    @DisplayName("회원 가입시 회원가입한 날짜와 마지막 수정날짜가 동일합니다")
    public void 회원가입날짜_수정날짜() throws Exception {
        // given
        Address address = new Address("1234-12", "어딘가", "자세히");

        UserRequest userRequest = new UserRequest("test1", "test1!", "test1!", "테스터", "01012341234", "abc@naver.com", address, LocalDateTime.now(), null, 1000);


        // when
        User saveUser = userRepository.save(userRequest.toEntity());

        // then
        Assertions.assertThat(saveUser.getCreateDate()).isEqualTo(saveUser.getLastModifiedDate());

    }

    @Test
    @DisplayName("아이디 중복 ~를 하면 ~Exception이 반환된다.")
    public void 회원아이디_중복() throws Exception {
        // given
        String loginId = "회원가입";
        Address address = new Address("1234-12", "어딘가", "자세히");

        UserRequest userRequest1 = new UserRequest("test1", "test1!", "test1!", "테스터", "01012341234", "abc@naver.com", address, LocalDateTime.now(), null, 1000);
        userRepository.save(userRequest1.toEntity());
        UserRequest userRequest2 = new UserRequest("test1", "test1!", "test1!", "테스터", "01012341234", "abc@naver.com", address, LocalDateTime.now(), null, 1000);
        User saveUser2 = userRepository.save(userRequest2.toEntity());
        // when

        if (userRepository.existsByLoginId(saveUser2.getLoginId())) {
            loginId = "아이디 중복";
        }


        // then
        Assertions.assertThat(loginId).isEqualTo("아이디 중복");
    }


}