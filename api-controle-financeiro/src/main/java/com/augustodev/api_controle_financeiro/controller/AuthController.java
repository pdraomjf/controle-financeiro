package com.augustodev.api_controle_financeiro.controller;

import com.augustodev.api_controle_financeiro.dto.login.LoginRequest;
import com.augustodev.api_controle_financeiro.dto.login.LoginResponse;
import com.augustodev.api_controle_financeiro.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);

        if (loginResponse.getToken().equals("not_match")) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(loginResponse);
        }

    }

}
