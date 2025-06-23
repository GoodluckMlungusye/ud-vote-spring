package com.goodamcodes.dto.college;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollegeResponseDTO {

    private Long id;
    private String name;
    private String imageUrl;
    private int electionYear;
    private int registeredStudents;
    private int votedStudents;
    private int rating;
    private String percentage;

}
