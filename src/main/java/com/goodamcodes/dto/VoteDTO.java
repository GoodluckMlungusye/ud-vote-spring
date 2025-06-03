package com.goodamcodes.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VoteDTO {

    private Long id;
    private Long contestantId;
    private Long categoryId;
    private Long voterId;
    private Long electionId;

}
