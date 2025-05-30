package com.goodamcodes.repository.security;

import com.goodamcodes.model.Student;
import com.goodamcodes.model.security.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByStudent(Student student);
    Optional<OTP> findByOtp(String otp);
}
