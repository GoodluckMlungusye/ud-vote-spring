package com.goodamcodes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {

    private Long id;
    private Long userId;
    private Long collegeId;
    private String registrationNumber;
    private int registrationYear;
    private String imageUrl;

}
