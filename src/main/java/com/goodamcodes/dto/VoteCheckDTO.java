package com.goodamcodes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteCheckDTO {

    private Long voterId;
    private Long categoryId;
    private Long electionId;

}
