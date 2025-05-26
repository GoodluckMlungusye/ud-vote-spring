package com.goodamcodes.controller;

import com.goodamcodes.dto.ConfirmPasswordResetDTO;
import com.goodamcodes.dto.EmailRequestDTO;
import com.goodamcodes.dto.UserInfoRequestDTO;
import com.goodamcodes.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserInfoService userInfoService;

    @PostMapping("/new-password")
    public ResponseEntity<String> requestPasswordReset(@RequestBody EmailRequestDTO email) {
        return ResponseEntity.status(HttpStatus.OK).body(userInfoService.requestPasswordReset(email));
    }

    @PatchMapping("/confirm-password")
    public ResponseEntity<String> confirmPasswordReset(@RequestBody ConfirmPasswordResetDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(userInfoService.confirmPasswordReset(request));
    }

    @PatchMapping("/update-user/{userId}")
    public ResponseEntity<String> updateUserInfo(@PathVariable Long userId, @RequestBody UserInfoRequestDTO user) {
        return ResponseEntity.status(HttpStatus.OK).body(userInfoService.updateUserInfo(userId, user));
    }

    @GetMapping("/email")
    public ResponseEntity<String> extractUserEmail(@RequestParam String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userInfoService.extractUserEmail(username));
    }

}
