package com.goodamcodes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

    private Long id;
    private Long electionId;
    private String name;
    private String abbreviation;
    private Boolean isGeneral;

}
