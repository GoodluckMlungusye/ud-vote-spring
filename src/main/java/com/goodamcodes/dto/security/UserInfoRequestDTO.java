package com.goodamcodes.dto.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoRequestDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
