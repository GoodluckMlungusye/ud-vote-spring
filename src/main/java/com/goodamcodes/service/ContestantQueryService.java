package com.goodamcodes.service;

import com.goodamcodes.dto.contestant.ContestantResponseDTO;
import com.goodamcodes.mapper.ContestantMapper;
import com.goodamcodes.model.Contestant;
import com.goodamcodes.model.Student;
import com.goodamcodes.model.security.UserInfo;
import com.goodamcodes.repository.ContestantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContestantQueryService {

    private final ContestantRepository contestantRepository;
    private final ContestantMapper contestantMapper;
    private final VoteQueryService voteQueryService;

    public List<ContestantResponseDTO> getContestants(Long categoryId) {
        List<Contestant> contestants = contestantRepository.findByCategoryId(categoryId);

        return contestants.stream()
                .map(this::getContestantResponse)
                .collect(Collectors.toList());
    }

    public ContestantResponseDTO getContestant(Long categoryId) {
        Contestant contestant =  contestantRepository.findFirstByCategoryId(categoryId).orElseThrow(() ->
                new IllegalArgumentException("No contestant found in this category"));

        return getContestantResponse(contestant);
    }

    public ContestantResponseDTO getContestantResponse(Contestant contestant) {
        ContestantResponseDTO dto = contestantMapper.toResponseDTO(contestant);

        Student student = contestant.getStudent();
        UserInfo user = student.getUser();

        dto.setName(user.getFirstName() + " " + user.getLastName());
        dto.setImageUrl(student.getImageUrl());
        dto.setCollegeName(student.getCollege().getName());

        dto.setVotes(
                voteQueryService.countContestantVotesInCategory(
                        contestant.getId(),
                        contestant.getCategory().getId()
                )
        );

        dto.setPercentage(voteQueryService.getContestantVotePercent(contestant.getId(), contestant.getCategory().getId()));

        return dto;
    }
}