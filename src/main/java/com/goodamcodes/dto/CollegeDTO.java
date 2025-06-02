package com.goodamcodes.dto;

import com.goodamcodes.model.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CollegeDTO {

    private Long id;
    private String name;
    private String imageUrl;
    private List<StudentDTO> students;

}
