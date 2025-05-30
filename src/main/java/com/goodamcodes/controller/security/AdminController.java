package com.goodamcodes.controller.security;

import com.goodamcodes.dto.security.RoleUpdateRequestDTO;
import com.goodamcodes.dto.security.UserInfoResponseDTO;
import com.goodamcodes.enums.Role;
import com.goodamcodes.repository.security.UserInfoRepository;
import com.goodamcodes.service.security.AdminService;
import com.goodamcodes.service.security.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private final UserInfoService userInfoService;
    private final UserInfoRepository userInfoRepository;

    @GetMapping("/users")
    public ResponseEntity<List<UserInfoResponseDTO>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userInfoService.getAllUsers());
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.status(HttpStatus.OK).body(List.of(Role.values()));
    }

    @PutMapping("/assign-role")
    public ResponseEntity<String> assignRole(@RequestBody RoleUpdateRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.assignRole(request));
    }
}
