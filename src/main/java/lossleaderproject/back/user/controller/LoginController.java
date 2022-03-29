package lossleaderproject.back.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lossleaderproject.back.security.auth.PrincipalDetails;
import lossleaderproject.back.user.service.LoginService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@Api(tags = "카카오,네이버 로그인에 대한 API")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @ApiOperation(value = "네이버 로그인", notes = "네이버 로그인시 넘어오는 code값을 받은 뒤 jwt 토큰을 생성과 동시에 로그인 및 회원가입")
    @GetMapping("/lossleader/callback/naver")
    public void naverLogin(@RequestParam("code") String code,HttpServletResponse res) throws IOException {

        loginService.naverToken(code, res);
    }

    @ApiOperation(value = "카카오 로그인", notes = "카카오 로그인시 넘어오는 code값을 받은 뒤 jwt 토큰 생성과 동시에 회원가입 및 로그인")
    @GetMapping("/lossleader/callback/kakao")
    public void kakaoLogin(@RequestParam("code") String code, HttpServletResponse res,@ApiIgnore HttpSession session) throws IOException {
        loginService.kakaoToken(code, res, session);
    }

    @ApiOperation(value = "카카오 로그아웃", notes = "카카오에서 제공하는 accessToken값을 header에 넣고 로그아웃")
    @GetMapping("/logout")
    @ResponseBody
    public String kakaoLogout(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request, @ApiIgnore HttpSession session) throws IOException {
        System.out.println("제발");
        loginService.logout(principalDetails,request,session);
        System.out.println("로그아웃제바랍랍랍ㅈ라밥랍랍라바");
        return "로그아웃";
    }


}
