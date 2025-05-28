package com.goodamcodes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationYearDTO {

    private Long id;
    private Long collegeId;
    private Integer year;
    private Integer registeredStudents;
    private Integer votedStudents;

}
