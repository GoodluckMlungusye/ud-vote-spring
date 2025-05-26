package com.goodamcodes.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationErrorResponseDTO extends ErrorResponseDTO {

    private Map<String, String> errors;

    public ValidationErrorResponseDTO(int status, String message, LocalDateTime timestamp, Map<String, String> errors) {
        super(status, message, timestamp);
        this.errors = errors;
    }
}
