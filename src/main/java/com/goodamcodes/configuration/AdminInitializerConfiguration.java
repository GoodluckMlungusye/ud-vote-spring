package com.goodamcodes.configuration;

import com.goodamcodes.enums.Role;
import com.goodamcodes.model.UserInfo;
import com.goodamcodes.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Slf4j
@Configuration
public class AdminInitializerConfiguration {

    @Value("${admin.default.username}")
    private String defaultAdminUsername;

    @Value("${admin.default.email}")
    private String defaultAdminEmail;

    @Value("${admin.default.password}")
    private String defaultAdminPassword;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public CommandLineRunner initAdmin(UserInfoRepository repository, BCryptPasswordEncoder encoder) {
        return args -> {
            if (repository.findByUsername(defaultAdminUsername).isEmpty()) {
                UserInfo admin = UserInfo.builder()
                        .username(defaultAdminUsername)
                        .email(defaultAdminEmail)
                        .password(encoder.encode(defaultAdminPassword))
                        .roles(List.of(Role.ADMIN))
                        .build();

                repository.save(admin);
                log.info("Default admin created: username='{}'", defaultAdminUsername);
            } else {
                log.info("Admin '{}' already exists, skipping creation.", defaultAdminUsername);
            }
        };
    }
}
