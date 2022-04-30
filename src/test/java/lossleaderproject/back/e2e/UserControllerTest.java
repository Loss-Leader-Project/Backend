package lossleaderproject.back.e2e;

import lossleaderproject.back.error.userException.UserErrorResponse;
import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class UserControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    UserRepository userRepository;

    public void userSave() {
        userRepository.save(new User("lossleader", "asd123!", "정회운", "01011111111", "cousim55@gmail.com", null, null, null, "9812111", null));

    }

    @Test
    @DisplayName("회원가입 된 아이디 없으면 '사용가능한 아이디입니다'를 반환합니다.'")
    void 사용가능_아이디() throws Exception {
        //given
        userSave();
        String loginId = "test21";

        //when
        String response = webTestClient.get().uri(uriBuilder -> uriBuilder.path("/lossleader-user/loginid")
                        .queryParam("loginId", loginId).build()).accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange().expectBody(String.class).returnResult().getResponseBody();

        //then
        Assertions.assertEquals("사용가능한 아이디 입니다.",response);
    }

    @Test
    @DisplayName("회원가입 된 아이디 있으면 message : 이미 존재하는 아이디입니다.', " +
            "'status : 409'," +
                    "'Error : DUPLICATE_ID'를 반환합니다.")
    void 중복_아이디() throws Exception {
        //given
        userSave();
        String loginId = "lossleader";

        //when
        UserErrorResponse response = webTestClient.get().uri(uriBuilder -> uriBuilder.path("/lossleader-user/loginid")
                        .queryParam("loginId", loginId).build()).accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange().expectBody(UserErrorResponse.class).returnResult().getResponseBody();

        //then
        Assertions.assertEquals("이미 존재하는 아이디입니다.",response.getMessage());
        Assertions.assertEquals(409,response.getStatus());
        Assertions.assertEquals("DUPLICATE_ID",response.getCode());
    }
}
