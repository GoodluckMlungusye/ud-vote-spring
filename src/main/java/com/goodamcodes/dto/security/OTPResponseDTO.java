package com.goodamcodes.dto.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OTPResponseDTO {
    private Long voterId;
    private String message;
}
