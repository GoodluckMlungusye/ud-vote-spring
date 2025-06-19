package com.goodamcodes.controller.security;

import com.goodamcodes.dto.security.UserInfoRequestDTO;
import com.goodamcodes.service.security.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserInfoService userInfoService;

    @PatchMapping("/update-user/{userId}")
    public ResponseEntity<String> updateUserInfo(@PathVariable Long userId, @RequestBody UserInfoRequestDTO user) {
        return ResponseEntity.status(HttpStatus.OK).body(userInfoService.updateUserInfo(userId, user));
    }

    @GetMapping("/email")
    public ResponseEntity<String> extractUserEmail(@RequestParam String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userInfoService.extractUserEmail(username));
    }

}
