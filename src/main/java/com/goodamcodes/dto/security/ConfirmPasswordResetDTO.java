package com.goodamcodes.dto.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmPasswordResetDTO {
    private String email;
    private String code;
    private String newPassword;
    private String confirmPassword;
}
