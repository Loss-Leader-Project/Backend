package lossleaderproject.back.user.controller;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.user.dto.*;
import lossleaderproject.back.user.exception.ErrorCode;
import lossleaderproject.back.user.exception.UserCustomException;
import lossleaderproject.back.user.service.MailService;
import lossleaderproject.back.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MailService mailService;

    @PatchMapping("/lossleader-user")
    public String mail(HttpSession session, @Valid @RequestBody SendEmail eMail) {
        mailService.sendMail(session, eMail.getEmail());
        return "메일 발송성공";
    }


    @PutMapping("/lossleader-user")
    public String emailVerification(HttpSession session,@RequestBody EmailVerificationNumber inputCode) {
        return mailService.emailVerification(session, inputCode.getNumber());
    }

    @PostMapping("/lossleader-user")
    public ResponseEntity<String> newMember(@Valid @RequestBody UserRequest userRequest) {

        if (userService.checkLoginId(userRequest.getLoginId())) {
            throw new UserCustomException(ErrorCode.DUPLICATE_ID);
        }

        if (userService.checkRecommendedPerson(userRequest.getRecommendedPerson()) == false) {
            throw new UserCustomException(ErrorCode.RECOMMENDED_USER_NOT_FOUND);
        }

        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new UserCustomException(ErrorCode.DISMATCH_PASSWORD);
        }
        // 아이디 중복 안했을시 회원 저장

        userService.save(userRequest);
        return ResponseEntity.ok("회원가입 성공");
        // { body: { id:5, message: '회원가입 성공' } }
    }

    @GetMapping("/userinfo/{userId}")
    public ResponseEntity<UserResponse> userInfoDetail(@PathVariable("userId") Long userId) {

        return new ResponseEntity<UserResponse>(userService.userInfoDetail(userId), HttpStatus.OK);
    }


    @PostMapping("/userinfo/{userId}")
    public ResponseEntity<String> userInfo(@PathVariable("userId") Long userId, @RequestBody UserResponse userResponse) {
        if (userService.checkRecommendedPerson(userResponse.getRecommendedPerson()) == false) {
            throw new UserCustomException(ErrorCode.RECOMMENDED_USER_NOT_FOUND);
        }
        if (userService.userInfoRePasswordOldCheck(userId, userResponse.getOldPassword()) == false
                || userService.userInfoRePasswordNewCheck(userId, userResponse) == false) {
            throw new UserCustomException(ErrorCode.DISMATCH_PASSWORD);
        }


        userService.userInfoEdit(userId, userResponse);

        return ResponseEntity.ok("회원수정완료");
    }

    @PostMapping("/user/login-id")
    public ResponseEntity<UserLoginIdResponse> userLoginIdFind(@RequestBody UserLoginIdFindRequest userLoginIdFindRequest) {
        return new ResponseEntity<>(userService.findLoginId(userLoginIdFindRequest), HttpStatus.OK);
    }


}
