package lossleaderproject.back.user.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.config.Config;
import lossleaderproject.back.security.auth.PrincipalDetails;
import lossleaderproject.back.security.oauth.provider.TokenProvider;
import lossleaderproject.back.user.dto.UserRequest;
import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.user.repository.UserRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final TokenProvider tokenProvider;
    private final BCryptPasswordEncoder encode;
    private final UserRepository userRepository;
    private final Config config;


    public MultiValueMap<String, String> accessTokenParams(String grantType, String clientId,String code,String redirect_uri) {
        MultiValueMap<String, String> accessTokenParams = new LinkedMultiValueMap<>();
        accessTokenParams.add("grant_type", grantType);
        accessTokenParams.add("client_id", clientId);
        // accessTokenParams.add("client_secret", clientSecret);
        accessTokenParams.add("code", code); // ???????????? ?????? ??????
        accessTokenParams.add("redirect_uri", redirect_uri); // ???????????? ?????? ??????
        return accessTokenParams;
    }


    @Transactional
    public void naverToken(String code, HttpServletResponse response) throws IOException {
        try {
            JSONParser jsonParser = new JSONParser();
            String header = "Bearer " + code;
            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("Authorization", header);
            String responseBody = get(config.getNAVER_USER_INFO_URI(), requestHeaders);
            JSONObject parse = (JSONObject) jsonParser.parse(responseBody);

            JSONObject responseParse = (JSONObject) parse.get("response");
            String encodeUserName = (String) responseParse.get("name");
            String loginId = (String) responseParse.get("id");
            String email = (String) responseParse.get("email");
            String phoneNumber = (String) responseParse.get("mobile_e164");
            String userName = new String(encodeUserName.getBytes(StandardCharsets.UTF_8));
            User user = new UserRequest("social_" + loginId, userName, encode.encode("?????????"), email, phoneNumber).naverOAuthToEntity();
            if (userRepository.existsByLoginId(user.getLoginId()) == false) {
                userRepository.save(user);
            }
            String access_token = tokenProvider.create(new PrincipalDetails(user));
            response.addHeader("Authorization","Bearer " + access_token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Transactional
    public void kakaoToken(String code, HttpServletResponse res, HttpSession session) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        MultiValueMap<String, String> accessTokenParams = accessTokenParams("authorization_code",config.getKAKAO_CLIENT_ID() ,code,config.getKAKAO_REDIRECT_URI());
        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(accessTokenParams, headers);
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                config.getKAKAO_TOKEN_URI(),
                HttpMethod.POST,
                accessTokenRequest,
                String.class);
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(accessTokenResponse.getBody());
            session.setAttribute("Authorization", jsonObject.get("access_token"));
            String header = "Bearer " + jsonObject.get("access_token");
            System.out.println("header = " + header);
            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("Authorization", header);
            String responseBody = get(config.getKAKAO_USER_INFO_URI(), requestHeaders);

            JSONObject profile = (JSONObject) jsonParser.parse(responseBody);
            JSONObject properties = (JSONObject) profile.get("properties");
            JSONObject kakao_account = (JSONObject) profile.get("kakao_account");

            Long loginId = (Long) profile.get("id");
            String email = (String) kakao_account.get("email");
            String userName = (String) properties.get("nickname");
            User kakaoUser = new UserRequest("social_" + loginId, encode.encode("?????????"), userName, email).kakaoOAuthToEntity();
            if (userRepository.existsByLoginId(kakaoUser.getLoginId()) == false) {
                userRepository.save(kakaoUser);
            }
            String access_token = tokenProvider.create(new PrincipalDetails(kakaoUser));
            res.setHeader("Authorization", "Bearer "+access_token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(PrincipalDetails principalDetails, HttpServletRequest request, HttpSession session) throws IOException {
        String role = userRepository.findByLoginId(principalDetails.getUsername()).getRole();
        String authorization = request.getHeader("Authorization");
        if(role.equals("ROLE_KAKAO")) {
            String access_token = (String) session.getAttribute("Authorization");
            if (access_token != null && !"".equals(access_token)) {
                HttpURLConnection connect = connect("https://kapi.kakao.com/v1/user/logout");
                connect.setRequestMethod("POST");
                connect.setRequestProperty("Authorization", "Bearer " + access_token);
                readBody(connect.getInputStream());
                session.removeAttribute("access_token");
            }
        }else if(role.equals("ROLE_NAVER")) {
            HttpURLConnection connect = connect("https://nid.naver.com/nidlogin.logout");
            connect.setRequestMethod("GET");
            connect.setRequestProperty("Authorization", "Bearer " + authorization);
            readBody(connect.getInputStream());
        }
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            } else {
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API ????????? ?????? ??????", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL??? ?????????????????????. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("????????? ??????????????????. : " + apiUrl, e);
        }
    }



    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API ????????? ????????? ??????????????????.", e);
        }
    }
}

