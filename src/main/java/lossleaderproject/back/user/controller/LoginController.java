package lossleaderproject.back.user.controller;

import lossleaderproject.back.user.dto.Login;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {

    @GetMapping("/login")
    public ResponseEntity<Login> inputLogin(Login login) {
        return new ResponseEntity<>(login, HttpStatus.OK);
    }


}
