package com.goodamcodes.service;

import com.goodamcodes.dto.security.RoleUpdateRequestDTO;
import com.goodamcodes.enums.Role;
import com.goodamcodes.model.security.UserInfo;
import com.goodamcodes.repository.security.UserInfoRepository;
import com.goodamcodes.service.security.AdminService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @InjectMocks
    private AdminService adminService;

    private UserInfo user;

    @BeforeEach
    void setUp() {
        user = new UserInfo();
        user.setUsername("john");
        user.setRoles(new ArrayList<>(List.of(Role.USER)));
    }

    @Test
    void shouldAssignNewRoleSuccessfully() {
        RoleUpdateRequestDTO request = new RoleUpdateRequestDTO();
        request.setUsername("john");
        request.setRole("ADMIN");

        when(userInfoRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(userInfoRepository.save(any(UserInfo.class))).thenReturn(user);

        String result = adminService.assignRole(request);

        assertEquals("Role assigned successfully.", result);
        assertTrue(user.getRoles().contains(Role.ADMIN));
        verify(userInfoRepository).save(user);
    }

    @Test
    void shouldReturnAlreadyHasRoleMessage() {
        RoleUpdateRequestDTO request = new RoleUpdateRequestDTO();
        request.setUsername("john");
        request.setRole("USER");

        when(userInfoRepository.findByUsername("john")).thenReturn(Optional.of(user));

        String result = adminService.assignRole(request);

        assertEquals("User already has this role.", result);
        verify(userInfoRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        RoleUpdateRequestDTO request = new RoleUpdateRequestDTO();
        request.setUsername("unknown");
        request.setRole("ADMIN");

        when(userInfoRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> adminService.assignRole(request));
        verify(userInfoRepository, never()).save(any());
    }
}
