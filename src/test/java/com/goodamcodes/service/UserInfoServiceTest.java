package com.goodamcodes.service;

import com.goodamcodes.dto.security.UserInfoResponseDTO;
import com.goodamcodes.mapper.UserInfoMapper;
import com.goodamcodes.model.security.UserInfo;
import com.goodamcodes.repository.security.UserInfoRepository;
import com.goodamcodes.service.security.UserInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserInfoServiceTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private UserInfoMapper userInfoMapper;

    @InjectMocks
    private UserInfoService userInfoService;

    private UserInfo user;

    @BeforeEach
    void setUp() {
        user = new UserInfo();
        user.setUsername("john");
        user.setPassword("secret");
    }

    @Nested
    @DisplayName("loadUserByUsername() Tests")
    class loadUserByUsernameTests {

        @Test
        void shouldLoadUserByUsernameSuccessfully() {
            when(userInfoRepository.findByUsername("john")).thenReturn(Optional.of(user));

            var result = userInfoService.loadUserByUsername("john");

            assertNotNull(result);
            assertEquals("john", result.getUsername());
            verify(userInfoRepository).findByUsername("john");
        }

        @Test
        void shouldThrowWhenUserNotFound() {
            when(userInfoRepository.findByUsername("missing")).thenReturn(Optional.empty());

            assertThrows(UsernameNotFoundException.class, () -> userInfoService.loadUserByUsername("missing"));

            verify(userInfoRepository).findByUsername("missing");
        }

    }

    @Nested
    @DisplayName("getAllUsers() Tests")
    class getAllUsersTests {

        @Test
        void shouldReturnAllUsersMappedToDTO() {
            List<UserInfo> users = List.of(user);
            UserInfoResponseDTO dto = new UserInfoResponseDTO();
            dto.setUsername("john");
            when(userInfoRepository.findAll()).thenReturn(users);
            when(userInfoMapper.toUserInfoResponseDTO(user)).thenReturn(dto);

            List<UserInfoResponseDTO> result = userInfoService.getAllUsers();

            assertEquals(1, result.size());
            assertEquals("john", result.getFirst().getUsername());
            verify(userInfoMapper).toUserInfoResponseDTO(user);
        }

        @Test
        void shouldReturnEmptyListIfNoUsers() {
            when(userInfoRepository.findAll()).thenReturn(Collections.emptyList());

            List<UserInfoResponseDTO> result = userInfoService.getAllUsers();

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("userExists() Tests")
    class userExistsTests {

        @Test
        void shouldReturnTrueIfUserExists() {
            when(userInfoRepository.findByUsername("john")).thenReturn(Optional.of(user));

            boolean exists = userInfoService.userExists("john");

            assertTrue(exists);
        }

        @Test
        void shouldReturnFalseIfUserDoesNotExist() {
            when(userInfoRepository.findByUsername("unknown")).thenReturn(Optional.empty());

            boolean exists = userInfoService.userExists("unknown");

            assertFalse(exists);
        }
    }


}
