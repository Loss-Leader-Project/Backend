package lossleaderproject.back.user.service;

import lossleaderproject.back.user.dto.UserRequest;
import lossleaderproject.back.user.dto.UserResponse;
import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Test
    @DisplayName("사용자가 회원가입할때마다 i가 1씩 증가한다.") // 의미 없음
    // 생성된 id는 1씩 증가한다.
    public void 회원가입() throws Exception {
        // given
        UserRequest userRequest = new UserRequest("test1", "test1!", "test1!", "테스터", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", "9810151", null, 1000);
        UserRequest userRequest2 = new UserRequest("lossleader", "test1!", "test1!", "lossleader", "01012341234", "lossleader@naver.com", "1234-12223", "경기도", "부천시", "9201302", "test1", 1000);

        // when
        User saveUser = userRepository.save(userRequest.toEntity());
        User saveUser2 = userRepository.save(userRequest2.toEntity());

        // then
        Assertions.assertThat(saveUser.getId()).isEqualTo(1);
        Assertions.assertThat(saveUser2.getId()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원가입시 마일리지 3000 마일리지가 충전됩니다.")
    public void 회원가입_마일리지() throws Exception {
        // given
        UserRequest userRequest = new UserRequest("test1", "test1!", "test1!", "테스터", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", "9810151", null, 1000);

        // when
        User savedUser = userRepository.save(userRequest.toEntity());
        // then
        Assertions.assertThat(savedUser.getMileage()).isEqualTo(3000);

    }

    @Test
    @DisplayName("회원 가입시 회원가입한 날짜와 마지막 수정날짜가 동일합니다")
    public void 회원가입날짜_수정날짜() throws Exception {
        // given
        UserRequest userRequest = new UserRequest("test1", "test1!", "test1!", "테스터", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", "9810151", null, 1000);


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


        UserRequest userRequest1 = new UserRequest("test1", "test1!", "test1!", "테스터", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", "9810151", null, 1000);
        userRepository.save(userRequest1.toEntity());
        UserRequest userRequest2 = new UserRequest("test1", "test1!", "test1!", "테스터", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", "9201302", null, 1000);
        User saveUser2 = userRepository.save(userRequest2.toEntity());
        // when

        if (userRepository.existsByLoginId(saveUser2.getLoginId())) {
            loginId = "아이디 중복";
        }


        // then
        Assertions.assertThat(loginId).isEqualTo("아이디 중복");
    }

    @Test
    @DisplayName("회원가입한 회원이 추천인 아이디를 입력할 시 회원과 해당 추천인 아이디 회원 각 500마일리지가 추가됩니다.")
    @Transactional
    public void 회원가입_추천인아이디() throws Exception {
        // given
        UserRequest userRequest1 = new UserRequest("test1", "test1!", "test1!", "테스터", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", "9810151", null, 1000);
        UserRequest userRequest2 = new UserRequest("lossleader", "test1!", "test1!", "lossleader", "01012341234", "lossleader@naver.com", "1234-12223", "경기도", "부천시", "9201302", "test1", 1000);

        // when
        Long saveId1 = userService.save(userRequest1);
        Long saveId2 = userService.save(userRequest2);

        User user = userRepository.findById(saveId1).get();
        User user2 = userRepository.findById(saveId2).get();

        // then
        Assertions.assertThat(user.getMileage()).isEqualTo(3500);
        Assertions.assertThat(user2.getMileage()).isEqualTo(3500);
    }

    @Test
    @DisplayName("회원이 수정한 정보만 수정됩니다.")
    @Transactional
    public void 회원정보_수정() throws Exception {
        // given
        UserRequest userRequest1 = new UserRequest("test1", "test1!", "test1!", "테스터", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", "9810151", null, 1000);
        User savedUser = userRepository.save(userRequest1.toEntity());
        // when
        UserResponse userResponse = new UserResponse(null,"bye",null,null,null,null);
        userService.userInfoEdit(savedUser.getId(), userResponse);

        // then
        Assertions.assertThat(savedUser.getUserName()).isEqualTo("bye");
        Assertions.assertThat(savedUser.getPhoneNumber()).isEqualTo("01012341234");
    }

    @Test
    @DisplayName("회원정보 수정에서 추천인 아이디를 추가해도 마일리지는 추가되지 않습니다.")
    @Transactional
    public void 회원정보_추천인_마일리지() throws Exception {
        // given
        UserRequest userRequest1 = new UserRequest("test1", "test1!", "test1!", "테스터", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", "9810151", null, 1000);
        UserRequest userRequest2 = new UserRequest("lossleader", "test1!", "test1!", "lossleader", "01012341234", "lossleader@naver.com", "1234-12223", "경기도", "부천시", "9201302", "test1", 1000);
        Long userId1 = userService.save(userRequest1);
        Long userId2 = userService.save(userRequest2);

        // when
        User savedUser1 = userRepository.findById(userId1).get();
        User savedUser2 = userRepository.findById(userId2).get();
        UserResponse userResponse = new UserResponse(null,null,null,null,null,"lossleader");
        userService.userInfoEdit(userId1, userResponse);
        // then
        Assertions.assertThat(savedUser1.getRecommendedPerson()).isEqualTo("lossleader");
        Assertions.assertThat(savedUser1.getMileage()).isEqualTo(3500);
        Assertions.assertThat(savedUser2.getMileage()).isEqualTo(3500);
    }

    @Test
    @DisplayName("회원이름, 생년월일, 이메일로 회원 아이디 찾기")
    public void 아이디찾기() throws Exception {
        // given
        UserRequest userRequest1 = new UserRequest("test1", "test1!", "test1!", "테스터", "01012341234", "abc@naver.com", "1234-123", "간략주소", "상세주소", "9810151", null, 1000);
        UserRequest userRequest2 = new UserRequest("lossleader", "test1!", "test1!", "정회운", "01012341234", "lossleader@naver.com", "1234-12223", "경기도", "부천시", "9810151", "test1", 1000);
        userService.save(userRequest1);
        userService.save(userRequest2);
        // when
        String loginId1 = userRepository.findLoginId(userRequest1.getUserName(), userRequest1.getBirthDate(), userRequest1.getEmail());
        String loginId2 = userRepository.findLoginId(userRequest2.getUserName(), userRequest2.getBirthDate(), userRequest2.getEmail());
        //then
        Assertions.assertThat(loginId1).isEqualTo("test1");
        Assertions.assertThat(loginId2).isEqualTo("lossleader");

    }


}