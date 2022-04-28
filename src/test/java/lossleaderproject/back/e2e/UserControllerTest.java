package lossleaderproject.back.e2e;

import lossleaderproject.back.error.userException.UserErrorResponse;
import lossleaderproject.back.user.dto.UserLoginIdFindRequest;
import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.user.repository.UserRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.transaction.Transactional;

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
        userRepository.save(new User("test22", "asd23123!", "테스터", "01022222222", "wjdghldns5048@naver.com", null, null, null, "9812111", null));

    }
    @Test
    @DisplayName("이름, 주민번호, 이메일로 해당 아이디의 일부를 반환합니다.")
    @Transactional()
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
        Assertions.assertEquals(userLoginId,"losslea***");
    }

    @Test
    @DisplayName("이름, 주민번호, 이메일 중 하나라도 올바르지않을 경우 " +
            "message : 사용자 이름, 생년월일, 이메일을 잘못입력하셨습니다.', " +
            "'status : 404'," +
            "'Error : Not_Found'")
    void 이름_이메일_주민번호_잘못작성() throws Exception {
        //given
        userSave();
        String userName = "정화운";
        String email = "cousim55@gmail.com";
        String birthDate = "9812111";
        //when
        UserErrorResponse response = webTestClient.post().uri("/login/id")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .bodyValue(new UserLoginIdFindRequest(userName,birthDate,email))
                .exchange().expectBody(UserErrorResponse.class).returnResult().getResponseBody();
        //then
        Assertions.assertEquals(response.getMessage(),"사용자 이름, 생년월일, 이메일을 잘못입력하셨습니다.");
        Assertions.assertEquals(response.getCode(),"NO_EXIST_USERNAME_BIRTHDATE_EMAIL");
        Assertions.assertEquals(response.getStatus(),404);
        Assertions.assertEquals(response.getError(),"NOT_FOUND");

    }


}
