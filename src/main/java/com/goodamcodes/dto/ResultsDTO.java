package com.goodamcodes.dto;
import com.goodamcodes.dto.contestant.ContestantResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResultsDTO {

    private List<ContestantResponseDTO> contestants;
    private int totalVotes;

}
