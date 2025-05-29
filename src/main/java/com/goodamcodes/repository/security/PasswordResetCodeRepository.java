package com.goodamcodes.repository.security;

import com.goodamcodes.model.security.PasswordResetCode;
import com.goodamcodes.model.security.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetCodeRepository extends JpaRepository<PasswordResetCode, Long> {
    Optional<PasswordResetCode> findByUser(UserInfo user);
}
