package com.goodamcodes.service.security;

import com.goodamcodes.dto.security.TokenResponseDTO;
import com.goodamcodes.dto.security.UserAuthenticationDTO;
import com.goodamcodes.dto.security.UserInfoRequestDTO;
import com.goodamcodes.enums.Role;
import com.goodamcodes.model.security.UserInfo;
import com.goodamcodes.repository.security.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserInfoRepository userInfoRepository;
    private final JwtService jwtService;
    private final UserInfoService userInfoService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String register(UserInfoRequestDTO userInfoRequestDTO) {
        if(userInfoService.userExists(userInfoRequestDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        UserInfo user = new UserInfo();
        user.setFirstName(userInfoRequestDTO.getFirstName());
        user.setLastName(userInfoRequestDTO.getLastName());
        user.setUsername(userInfoRequestDTO.getUsername());
        user.setPassword(encoder.encode(userInfoRequestDTO.getPassword()));
        user.setEmail(userInfoRequestDTO.getEmail());
        user.setRoles(List.of(Role.USER));

        UserInfo registeredUser = userInfoRepository.save(user);

        return "User " + registeredUser.getUsername() + " registered successfully!";
    }

    public TokenResponseDTO authenticate(UserAuthenticationDTO user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(!authentication.isAuthenticated()){
            throw new IllegalArgumentException("User not authenticated");
        }
        UserInfo savedUser = userInfoRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new TokenResponseDTO(jwtService.generateToken(savedUser));
    }
}

