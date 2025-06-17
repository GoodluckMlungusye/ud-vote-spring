package com.goodamcodes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ContestantDetailsDTO {
    private String name;
    private String imageUrl;
    private String collegeName;
}
