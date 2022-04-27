package lossleaderproject.back.e2e;

import lossleaderproject.back.user.dto.UserLoginIdFindRequest;
import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
    @DisplayName("이름, 주민번호, 이메일로 해당 아이디의 일부를 반환합니다.")
    void 아이디찾기() throws Exception {
        //given
        userSave();
        String userName = "정회운";
        String birthDate = "9812111";
        String email = "cousim55@gmail.com";
        //when
        String loginId = webTestClient.post().uri("/login/id")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .bodyValue(new UserLoginIdFindRequest(userName,birthDate,email))
                .exchange().expectBody(String.class).returnResult().getResponseBody();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(loginId);
        String userLoginId = (String)jsonObject.get("loginId");

        //then
        Assertions.assertThat(userLoginId).isEqualTo("losslea***");
    }
}
