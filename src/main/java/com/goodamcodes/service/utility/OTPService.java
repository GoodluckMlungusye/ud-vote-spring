package com.goodamcodes.service.utility;

import com.goodamcodes.model.Student;
import com.goodamcodes.model.security.OTP;
import com.goodamcodes.repository.security.OTPRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OTPService {

    private final OTPRepository otpRepository;

    public String createOTP() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public void saveOTP(String otp, Student student, LocalDateTime expiry) {
        OTP existingOTP = otpRepository.findByStudent(student).orElse(null);

        if (existingOTP != null) {
            existingOTP.setOtp(otp);
            existingOTP.setExpiryDate(expiry);
            otpRepository.save(existingOTP);
        } else {
            OTP newOTP = new OTP();
            newOTP.setOtp(otp);
            newOTP.setStudent(student);
            newOTP.setExpiryDate(expiry);
            otpRepository.save(newOTP);
        }
    }

    public void deleteByStudent(Student student) {
        otpRepository.findByStudent(student)
                .ifPresent(otpRepository::delete);
    }
}
