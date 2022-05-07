package lossleaderproject.back.e2e;


import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.user.mail.dto.SendEmailRequest;
import lossleaderproject.back.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "10000")
@Transactional
class UserControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    UserRepository userRepository;
    @BeforeEach
    public void saveUser() {
        userRepository.save(new User("lossleader", "정회운", "asd123!", "01011111111", "cousim55@gmail.com", null, null, null, "9812111", null));
    }

    @Test
    @DisplayName("가입 안된 유저의 이메일 주소를 받았을때 메일 발송")
    public void 메일발송() throws Exception {
        // given
        String email = "wjdghldns5048@naver.com";
        //when
        String result = webTestClient.post().uri("/lossleader-user/email").contentType(MediaType.APPLICATION_JSON_UTF8)
                .bodyValue(new SendEmailRequest(email))
                .exchange().expectBody(String.class).returnResult().getResponseBody();
        //then
        Assertions.assertThat(result).isEqualTo("메일 발송성공");

    }


}
