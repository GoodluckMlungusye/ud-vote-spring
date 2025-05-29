package com.goodamcodes.dto.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthenticationDTO {

    private String username;
    private String password;

}
