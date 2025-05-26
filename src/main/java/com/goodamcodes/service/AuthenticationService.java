package com.goodamcodes.service;

import com.goodamcodes.enums.Role;
import com.goodamcodes.model.UserInfo;
import com.goodamcodes.repository.UserInfoRepository;
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

    public UserInfo register(UserInfo user) {
        if(userInfoService.userExists(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(List.of(Role.USER));
        return userInfoRepository.save(user);
    }

    public String authenticate(UserInfo user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            UserInfo savedUser = userInfoRepository.findByUsername(user.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return jwtService.generateToken(savedUser);
        } else {
            return "User not authenticated";
        }
    }
}

