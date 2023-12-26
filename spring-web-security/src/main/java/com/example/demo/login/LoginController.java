package com.example.demo.login;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest
    ) {
        if ("john".equals(loginRequest.username()) && "123".equals(loginRequest.password())) {
            // generate token for user
            String token = "generate-some-token-for-john-as-admin";
            // return 200 ok with token
            LoginResponse loginResponse = new LoginResponse(token);
            return ResponseEntity.ok().body(loginResponse);
        } else if ("mike".equals(loginRequest.username()) && "123".equals(loginRequest.password())) {
            // generate token for user
            String token = "generate-some-token-for-mike-as-user";
            // return 200 ok with token
            LoginResponse loginResponse = new LoginResponse(token);
            return ResponseEntity.ok().body(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
