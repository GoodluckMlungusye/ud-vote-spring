package com.goodamcodes.service.security;
import com.goodamcodes.dto.security.ConfirmPasswordResetDTO;
import com.goodamcodes.dto.security.EmailRequestDTO;
import com.goodamcodes.dto.security.UserInfoRequestDTO;
import com.goodamcodes.dto.security.UserInfoResponseDTO;
import com.goodamcodes.enums.Role;
import com.goodamcodes.mapper.UserInfoMapper;
import com.goodamcodes.model.security.PasswordResetCode;
import com.goodamcodes.model.security.UserInfo;
import com.goodamcodes.repository.security.PasswordResetCodeRepository;
import com.goodamcodes.repository.security.UserInfoRepository;
import com.goodamcodes.service.utility.EmailService;
import com.goodamcodes.service.utility.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordResetCodeService  passwordResetCodeService;
    private final PasswordResetCodeRepository passwordResetCodeRepository;
    private final EmailService emailService;
    private final OTPService otpService;
    private final UserInfoMapper userInfoMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userInfoRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public String requestPasswordReset(EmailRequestDTO emailRequestDTO) {
        UserInfo user = userInfoRepository.findByEmail(emailRequestDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String code = otpService.createOTP();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(10);

        passwordResetCodeService.savePasswordResetCode(code,user,expiry);

        emailService.sendEmail(
                user.getEmail(),
                "Your Password Reset Code",
                code
        );

        return "Password Reset Code sent to " + user.getEmail();
    }

    public String confirmPasswordReset(ConfirmPasswordResetDTO confirmPasswordResetDTO) {
        if (!confirmPasswordResetDTO.getNewPassword().equals(confirmPasswordResetDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        UserInfo user = userInfoRepository.findByEmail(confirmPasswordResetDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        PasswordResetCode resetCode = passwordResetCodeRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("No reset code found"));

        if (!resetCode.getCode().equals(confirmPasswordResetDTO.getCode())) {
            throw new IllegalArgumentException("Invalid code.");
        }

        if (resetCode.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Code expired.");
        }

        user.setPassword(encoder.encode(confirmPasswordResetDTO.getNewPassword()));
        userInfoRepository.save(user);
        passwordResetCodeService.deleteByUser(user);
        return "Password was reset successfully";
    }

    public List<UserInfoResponseDTO> getAllUsers() {
        return userInfoRepository.findAll().stream().map(userInfoMapper::toUserInfoResponseDTO).collect(Collectors.toList());
    }

    public boolean userExists(String username) {
        return userInfoRepository.findByUsername(username).isPresent();
    }

    public String updateUserInfo(Long userId, UserInfoRequestDTO userInfoRequestDTO) {
        UserInfo existingUser = userInfoRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        existingUser.setFirstName(userInfoRequestDTO.getFirstName());
        existingUser.setLastName(userInfoRequestDTO.getLastName());
        existingUser.setUsername(userInfoRequestDTO.getUsername());
        existingUser.setEmail(userInfoRequestDTO.getEmail());

        userInfoRepository.save(existingUser);

        return "User updated successfully";
    }

    public String extractUserEmail(String username) {
        UserInfo userInfo = userInfoRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userInfo.getEmail();
    }

    public void addNewRoleToUser(UserInfo user, Role role) {
        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
        }
        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
        }
        userInfoRepository.save(user);
    }

}
