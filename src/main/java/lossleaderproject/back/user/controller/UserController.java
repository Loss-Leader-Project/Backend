package lossleaderproject.back.user.controller;

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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MailService mailService;

    @GetMapping("/lossleader-user")
    public ResponseEntity<String> duplicateLoginId(@RequestParam String loginId) {
        return new ResponseEntity<>(userService.checkLoginId(loginId),HttpStatus.OK);
    }

    @PatchMapping("/lossleader-user")
    public ResponseEntity<String> mail(HttpSession session, @Valid @RequestBody SendEmail eMail) {
        mailService.sendMail(session, eMail.getEmail());
        return new ResponseEntity<>("메일 발송성공",HttpStatus.OK);
    }


    @PutMapping("/lossleader-user")
    public ResponseEntity<String> emailVerification(HttpSession session,@RequestBody EmailVerificationNumber inputCode) {
        return new ResponseEntity<>(mailService.emailVerification(session, inputCode.getNumber()),HttpStatus.OK);
    }

    @PostMapping("/lossleader-user")
    public ResponseEntity<NewUserResponse> newMember(@Valid @RequestBody UserRequest userRequest) {
        Long saveId = userService.save(userRequest);
        return new ResponseEntity<>(new NewUserResponse(saveId, "회원가입 성공"), HttpStatus.OK);
    }

    @GetMapping("/user/info")
    public ResponseEntity<UserResponse> userInfoDetail(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        return new ResponseEntity<>(userService.userInfoDetail(principalDetails), HttpStatus.OK);
    }

    @PostMapping("/user/info")
    public ResponseEntity<String> userInfo(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody UserResponse userResponse) {

        userService.userInfoEdit(principalDetails.getUsername(), userResponse);

        return ResponseEntity.ok("회원수정완료");
    }

    @PostMapping("/user/login-id")
    public ResponseEntity<UserLoginIdResponse> userLoginIdFind(@Valid @RequestBody UserLoginIdFindRequest userLoginIdFindRequest) {
        return new ResponseEntity<>(userService.findLoginId(userLoginIdFindRequest), HttpStatus.OK);
    }

    @PostMapping("/user/login-password")
    public ResponseEntity<String> userLoginPasswordFind(@Valid @RequestBody UserFindPassword userFindPassword) {
        return new ResponseEntity<>(userService.findPassword(userFindPassword),HttpStatus.OK);
    }


}
