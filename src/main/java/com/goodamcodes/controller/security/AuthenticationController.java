package com.goodamcodes.controller.security;

import com.goodamcodes.dto.security.UserAuthenticationDTO;
import com.goodamcodes.dto.security.UserInfoRequestDTO;
import com.goodamcodes.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserInfoRequestDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody UserAuthenticationDTO user) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate(user));
    }
}
