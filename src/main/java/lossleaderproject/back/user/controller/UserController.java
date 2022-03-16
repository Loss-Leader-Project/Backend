package lossleaderproject.back.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lossleaderproject.back.security.auth.PrincipalDetails;
import lossleaderproject.back.user.dto.*;
import lossleaderproject.back.user.mail.dto.EmailVerificationNumber;
import lossleaderproject.back.user.mail.dto.SendEmail;
import lossleaderproject.back.user.mail.service.MailService;
import lossleaderproject.back.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@Api(tags = {"회원에 대한 API"})
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MailService mailService;

    @ApiOperation(value = "아이디 중복검사",notes = "해당 아이디가 이미 존재하는지에 대한 여부를 수행합니다.")
    @ApiImplicitParam(name = "loginId",value = "아이디",required = true,dataType = "String")
    @GetMapping("/lossleader-user")
    public ResponseEntity<String> duplicateLoginId(@RequestParam String loginId) {
        return new ResponseEntity<>(userService.checkLoginId(loginId),HttpStatus.OK);
    }

    @ApiOperation(value = "메일 발송",notes = "입력한 이메일로 인증번호가 발송이 됩니다.")
    @PatchMapping("/lossleader-user")
    public ResponseEntity<String> mail(@Valid @RequestBody SendEmail eMail,@ApiIgnore HttpSession session) {
        mailService.sendMail(session, eMail.getEmail());
        return new ResponseEntity<>("메일 발송성공",HttpStatus.OK);
    }

    @ApiOperation(value = "인증번호 인증",notes = "이메일을 통해 보내진 인증번호가 맞는지 수행합니다.")
    @PutMapping("/lossleader-user")
    public ResponseEntity<String> emailVerification(@Valid @RequestBody EmailVerificationNumber inputCode,@ApiIgnore HttpSession session) {
        return new ResponseEntity<>(mailService.emailVerification(session, inputCode.getNumber()),HttpStatus.OK);
    }

    @ApiOperation(value = "회원가입",notes = "회원가입을 수행합니다.")
    @PostMapping("/lossleader-user")
    public ResponseEntity<NewUserResponse> newMember( @Valid @RequestBody UserRequest userRequest,@ApiIgnore HttpSession session) {
        Long saveId = userService.save(userRequest,session);
        return new ResponseEntity<>(new NewUserResponse(saveId, "회원가입 성공"), HttpStatus.OK);
    }
    @ApiOperation(value = "로그인 한 회원에 대한 정보")
    @GetMapping("/user/info")
    public ResponseEntity<UserResponse> userInfoDetail(@ApiIgnore @AuthenticationPrincipal PrincipalDetails principalDetails) {

        return new ResponseEntity<>(userService.userInfoDetail(principalDetails), HttpStatus.OK);
    }

    @ApiOperation(value = "로그인 한 회원에 대한 정보수정")
    @PostMapping("/user/info")
    public ResponseEntity<String> userInfo(@ApiIgnore @AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody UserResponse userResponse) {

        userService.userInfoEdit(principalDetails.getUsername(), userResponse);

        return ResponseEntity.ok("회원수정완료");
    }

    @ApiOperation(value = "아이디 찾기")
    @PostMapping("/login-id")
    public ResponseEntity<UserLoginIdResponse> userLoginIdFind(@Valid @RequestBody UserLoginIdFindRequest userLoginIdFindRequest) {
        return new ResponseEntity<>(userService.findLoginId(userLoginIdFindRequest), HttpStatus.OK);
    }

    @ApiOperation(value = "비밀번호 찾기")
    @PostMapping("/login-password")
    public ResponseEntity<String> userLoginPasswordFind(@Valid @RequestBody UserFindPassword userFindPassword) {
        return new ResponseEntity<>(userService.findPassword(userFindPassword),HttpStatus.OK);
    }


}
