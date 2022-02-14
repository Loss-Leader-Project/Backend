package lossleaderproject.back.user.controller;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.user.dto.UserRequest;
import lossleaderproject.back.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/lossleader-member")
    public ResponseEntity<String> newMember(@Valid @RequestBody UserRequest userRequest) {

        // 인터셉터에 위치
        // 아이디 중복시 409 에러와 아이디 중복 리턴
        if (userService.checkLoginId(userRequest.getLoginId())) {
            return ResponseEntity.badRequest().body("이미 존재하는 아이디입니다.");
        }

        if(userService.checkRecommendedPerson(userRequest.getRecommendedPerson()) == false) {
            return ResponseEntity.badRequest().body("추천인 아이디가 존재하지 않습니다");
        }

        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }


        // 아이디 중복 안했을시 회원 저장

        userService.save(userRequest);
        return ResponseEntity.ok("회원가입 성공");
        // { body: { id:5, message: '회원가입 성공' } }
    }
}
