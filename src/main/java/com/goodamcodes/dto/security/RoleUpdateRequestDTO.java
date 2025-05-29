package com.goodamcodes.dto.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleUpdateRequestDTO {

    private String username;
    private String role;
}
