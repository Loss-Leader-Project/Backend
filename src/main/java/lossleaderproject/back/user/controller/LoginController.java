package lossleaderproject.back.user.controller;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.user.service.LoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;



    @GetMapping("/lossleader/callback/naver")
    public void naverLogin(@RequestParam("code") String code, @RequestParam("state") String state, HttpServletResponse res, HttpSession session) throws IOException {

        loginService.naverToken(code, state, res);
    }

    @GetMapping("/lossleader/callback/kakao")
    public void kakaoLogin(@RequestParam("code") String code, @RequestParam("state") String state, HttpServletResponse res, HttpSession session) throws IOException {

        loginService.kakaoToken(code, state, res, session);
    }

    @GetMapping("/logout/kakao")
    @ResponseBody
    public String kakaoLogout(HttpSession session) throws IOException {
        loginService.kakaoLogout(session);
        return "로그아웃";
    }


}
