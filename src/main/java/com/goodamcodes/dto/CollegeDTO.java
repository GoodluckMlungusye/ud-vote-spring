package com.goodamcodes.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CollegeDTO {

    private Long id;
    private String name;
    private String imageUrl;
    @JsonIgnore
    private List<StudentDTO> students;

}
