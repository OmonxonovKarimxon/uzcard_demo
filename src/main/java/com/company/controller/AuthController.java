package com.company.controller;

import com.company.dto.AuthDTO;
import com.company.dto.ResponseUserJwt;
import com.company.dto.company.RegistrationCompanyDTO;
import com.company.dto.profile.RegistretionProfileDTO;
import com.company.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/public/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthDTO dto) {

        ResponseUserJwt usernameWithJwt= authService.login(dto);
        return ResponseEntity.ok().body(usernameWithJwt);
    }


}
