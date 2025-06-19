package com.goodamcodes.controller.security;

import com.goodamcodes.dto.security.ConfirmPasswordResetDTO;
import com.goodamcodes.dto.security.EmailRequestDTO;
import com.goodamcodes.dto.security.UserAuthenticationDTO;
import com.goodamcodes.dto.security.UserInfoRequestDTO;
import com.goodamcodes.service.security.AuthenticationService;
import com.goodamcodes.service.security.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;
    private final UserInfoService userInfoService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserInfoRequestDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody UserAuthenticationDTO user) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate(user));
    }

    @PostMapping("/new-password")
    public ResponseEntity<String> requestPasswordReset(@RequestBody EmailRequestDTO email) {
        return ResponseEntity.status(HttpStatus.OK).body(userInfoService.requestPasswordReset(email));
    }

    @PatchMapping("/confirm-password")
    public ResponseEntity<String> confirmPasswordReset(@RequestBody ConfirmPasswordResetDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(userInfoService.confirmPasswordReset(request));
    }
}
