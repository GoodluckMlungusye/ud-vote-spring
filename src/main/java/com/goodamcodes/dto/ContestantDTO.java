package com.goodamcodes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContestantDTO {

    private Long id;
    private Long studentId;
    private Long categoryId;
    private String slogan;
    private String videoUrl;

}
