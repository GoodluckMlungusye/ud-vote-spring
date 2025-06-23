package com.goodamcodes.dto.contestant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContestantResponseDTO {

    private Long id;
    private Long studentId;
    private Long categoryId;
    private String name;
    private String slogan;
    private String videoUrl;
    private String imageUrl;
    private String collegeName;
    private int votes;
    private String percentage;
}

