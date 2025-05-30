package com.goodamcodes.service.security;

import com.goodamcodes.model.security.PasswordResetCode;
import com.goodamcodes.model.security.UserInfo;
import com.goodamcodes.repository.security.PasswordResetCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PasswordResetCodeService {

    private final PasswordResetCodeRepository passwordResetCodeRepository;

    public void savePasswordResetCode(String code, UserInfo user, LocalDateTime expiry) {
        PasswordResetCode existingCode = passwordResetCodeRepository.findByUser(user).orElse(null);

        if (existingCode != null) {
            existingCode.setCode(code);
            existingCode.setExpiryDate(expiry);
            passwordResetCodeRepository.save(existingCode);
        } else {
            PasswordResetCode resetCode = new PasswordResetCode();
            resetCode.setCode(code);
            resetCode.setUser(user);
            resetCode.setExpiryDate(expiry);
            passwordResetCodeRepository.save(resetCode);
        }
    }

    public void deleteByUser(UserInfo user) {
        passwordResetCodeRepository.findByUser(user)
                .ifPresent(passwordResetCodeRepository::delete);
    }

}
