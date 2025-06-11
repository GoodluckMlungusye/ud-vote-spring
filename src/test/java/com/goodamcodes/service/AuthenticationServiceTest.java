package com.goodamcodes.service;

import com.goodamcodes.dto.security.TokenResponseDTO;
import com.goodamcodes.dto.security.UserAuthenticationDTO;
import com.goodamcodes.dto.security.UserInfoRequestDTO;
import com.goodamcodes.model.security.UserInfo;
import com.goodamcodes.repository.security.UserInfoRepository;
import com.goodamcodes.service.security.AuthenticationService;
import com.goodamcodes.service.security.JwtService;
import com.goodamcodes.service.security.UserInfoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserInfoService userInfoService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authService;

    @Nested
    @DisplayName("register() Tests")
    class RegisterTests{

//        @Test
//        void shouldRegisterUserSuccessfully() {
//            UserInfoRequestDTO newUser = new UserInfoRequestDTO();
//            newUser.setUsername("john");
//            newUser.setPassword("password");
//            newUser.setEmail("example@gmail.com");
//
//            when(userInfoService.userExists("john")).thenReturn(false);
//            when(userInfoRepository.save(any(UserInfo.class))).thenAnswer(inv -> inv.getArgument(0));
//
//            UserInfo registered = userInfoMapper.toUserInfo(newUser);
//
//            assertNotNull(newUser);
//            assertEquals(1, registered.getRoles().size());
//            assertTrue(registered.getRoles().contains(Role.USER));
//            assertNotEquals("password", registered.getPassword());
//
//            verify(userInfoRepository).save(any(UserInfo.class));
//        }

        @Test
        void shouldThrowWhenUserAlreadyExists() {
            UserInfoRequestDTO newUser = new UserInfoRequestDTO();
            newUser.setUsername("existing");

            when(userInfoService.userExists("existing")).thenReturn(true);

            assertThrows(IllegalArgumentException.class, () -> authService.register(newUser));
        }

    }


    @Nested
    @DisplayName("authenticate() Tests")
    class AuthenticateTests{

        @Test
        void shouldAuthenticateAndReturnToken() {
            UserAuthenticationDTO loginUser = new UserAuthenticationDTO();
            loginUser.setUsername("john");
            loginUser.setPassword("password");

            Authentication auth = mock(Authentication.class);
            when(authenticationManager.authenticate(any())).thenReturn(auth);
            when(auth.isAuthenticated()).thenReturn(true);

            UserInfo savedUser = new UserInfo();
            savedUser.setUsername("john");

            when(userInfoRepository.findByUsername("john")).thenReturn(Optional.of(savedUser));
            when(jwtService.generateToken(savedUser)).thenReturn("mocked-jwt-token");

            TokenResponseDTO tokenResponseDTO = authService.authenticate(loginUser);

            assertEquals("mocked-jwt-token", tokenResponseDTO.getToken());
        }

        @Test
        void shouldReturnUnauthenticatedMessage() {
            UserAuthenticationDTO user = new UserAuthenticationDTO();
            user.setUsername("john");
            user.setPassword("wrong");

            Authentication auth = mock(Authentication.class);
            when(authenticationManager.authenticate(any())).thenReturn(auth);
            when(auth.isAuthenticated()).thenReturn(false);

            TokenResponseDTO tokenResponseDTOResult = authService.authenticate(user);

            assertEquals("User not authenticated", tokenResponseDTOResult.getToken());
        }

        @Test
        void shouldThrowIfUserNotFoundAfterAuthentication() {
            UserAuthenticationDTO user = new UserAuthenticationDTO();
            user.setUsername("missing");
            user.setPassword("secret");

            Authentication auth = mock(Authentication.class);
            when(authenticationManager.authenticate(any())).thenReturn(auth);
            when(auth.isAuthenticated()).thenReturn(true);

            when(userInfoRepository.findByUsername("missing")).thenReturn(Optional.empty());

            assertThrows(UsernameNotFoundException.class, () -> authService.authenticate(user));
        }

    }

}
