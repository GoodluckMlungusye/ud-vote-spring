package com.goodamcodes.mapper;

import com.goodamcodes.dto.VoteDTO;
import com.goodamcodes.model.Vote;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    @Mapping(target = "contestant", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "voter", ignore = true)
    @Mapping(target = "election", ignore = true)
    Vote toVote(VoteDTO voteDTO);

    @Mapping(target = "contestantId", source = "contestant.id")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "voterId", source = "voter.id")
    @Mapping(target = "electionId", source = "election.id")
    VoteDTO toVoteDTO(Vote vote);

}
