package com.goodamcodes.service;

import com.goodamcodes.service.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        try {
            var field = JwtService.class.getDeclaredField("SECRET_KEY");
            field.setAccessible(true);
            String secret = "mySuperSecretKey123456789012345678901234567890";
            field.set(jwtService, secret);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldGenerateAndValidateToken() {
        var userDetails = new User("john", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token, userDetails));
        assertEquals("john", jwtService.extractUsername(token));
    }

    @Test
    void shouldExtractClaimsCorrectly() {
        var userDetails = new User("jane", "password", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        String token = jwtService.generateToken(userDetails);

        String username = jwtService.extractUsername(token);
        @SuppressWarnings("unchecked")
        List<String> roles = jwtService.extractClaim(token, claims -> (List<String>) claims.get("roles"));

        assertEquals("jane", username);
        assertTrue(roles.contains("ROLE_ADMIN"));
    }

}
