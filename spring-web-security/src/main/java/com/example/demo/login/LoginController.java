package com.example.demo.login;

import com.example.demo.auth.JwtTokenGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    private final JwtTokenGenerator jwtTokenGenerator;

    public LoginController(JwtTokenGenerator jwtTokenGenerator) {
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest
    ) {
        if ("john".equals(loginRequest.username()) && "123".equals(loginRequest.password())) {
            // generate token for user
            String token = jwtTokenGenerator.generateToken(loginRequest.username(), "admin");
            // return 200 ok with token
            LoginResponse loginResponse = new LoginResponse(token);
            return ResponseEntity.ok().body(loginResponse);
        } else if ("mike".equals(loginRequest.username()) && "123".equals(loginRequest.password())) {
            // generate token for user
            String token = jwtTokenGenerator.generateToken(loginRequest.username(), "user");
            // return 200 ok with token
            LoginResponse loginResponse = new LoginResponse(token);
            return ResponseEntity.ok().body(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
