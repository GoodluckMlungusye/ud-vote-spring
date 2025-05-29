package com.goodamcodes.dto.error_exceptions;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDTO {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponseDTO(int status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
