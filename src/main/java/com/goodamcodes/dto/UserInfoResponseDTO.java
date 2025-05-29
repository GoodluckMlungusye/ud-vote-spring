package com.goodamcodes.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
}
