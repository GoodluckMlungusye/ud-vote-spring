package com.goodamcodes.repository;

import com.goodamcodes.model.PasswordResetCode;
import com.goodamcodes.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetCodeRepository extends JpaRepository<PasswordResetCode, Long> {
    Optional<PasswordResetCode> findByUser(UserInfo user);
}
