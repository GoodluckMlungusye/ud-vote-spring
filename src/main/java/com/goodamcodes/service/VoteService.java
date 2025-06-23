package com.goodamcodes.service;

import com.goodamcodes.dto.ResultsDTO;
import com.goodamcodes.dto.VoteDTO;
import com.goodamcodes.dto.contestant.ContestantResponseDTO;
import com.goodamcodes.model.*;
import com.goodamcodes.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final ContestantRepository contestantRepository;
    private final StudentRepository studentRepository;
    private final ElectionRepository electionRepository;
    private final CategoryRepository categoryRepository;
    private final ContestantQueryService contestantQueryService;
    private final VoteQueryService voteQueryService;

    @Transactional
    public String castVote(VoteDTO  voteDTO) {

        Contestant contestant = contestantRepository.findById(voteDTO.getContestantId())
                .orElseThrow(() -> new IllegalArgumentException("Contestant not found"));

        Category category = categoryRepository.findById(voteDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Student voter = studentRepository.findById(voteDTO.getVoterId())
                .orElseThrow(() -> new IllegalArgumentException("Voter not found"));

        Election election = electionRepository.findById(voteDTO.getElectionId())
                .orElseThrow(() -> new IllegalArgumentException("Election not found"));

        checkElectionWindow(election);

        checkVoterEligibilityByYear(election, voter);

        checkForValidContestant(contestantRepository, category, voteDTO);

        checkForDuplicateVotes(voter, category, election);

        Vote vote = Vote.builder()
                .voter(voter)
                .contestant(contestant)
                .category(category)
                .election(election)
                .build();

        voteRepository.save(vote);

        return "You have successfully voted for this category";
    }

    private void checkElectionWindow(Election election) {
        if (!election.hasStarted()) {
            throw new IllegalArgumentException("The election has not started yet");
        }
        if (election.hasEnded()) {
            throw new IllegalArgumentException("The election has already ended");
        }
    }

    private void checkVoterEligibilityByYear(Election election, Student voter) {
        if (election.getDate() == null || election.getStartTime() == null) {
            throw new IllegalArgumentException("Election date or start time is missing");
        }

        int electionYear = LocalDateTime.of(election.getDate(), election.getStartTime())
                .getYear();

        if (voter.getElectionYear() != electionYear) {
            throw new IllegalArgumentException(
                    "Voter is not eligible: registration year " +
                            voter.getElectionYear() + " does not match election year " + electionYear
            );
        }
    }

    private void checkForValidContestant(ContestantRepository contestantRepository, Category category, VoteDTO voteDTO) {
        boolean contestantExists = contestantRepository.existsByIdAndCategory(
                voteDTO.getContestantId(),
                category
        );
        if(!contestantExists) {
            throw new IllegalArgumentException("The contestant you are trying to vote for does not belong to this category");
        }
    }

    private void checkForDuplicateVotes(Student voter, Category category, Election election) {
        boolean hasVoted = voteRepository
                .existsByVoterAndCategoryAndElection(voter, category, election);
        if (hasVoted) {
            throw new IllegalArgumentException("You already voted for this category");
        }
    }

    public boolean hasVoted(Long categoryId, Long voterId) {
        return voteRepository.existsByCategoryIdAndVoterId(categoryId, voterId);
    }

    public ResultsDTO getCategoryResults(Long categoryId) {
        List<ContestantResponseDTO> contestants = contestantQueryService.getContestants(categoryId);
        int totalVotes = voteQueryService.countVotesInCategory(categoryId);

        return new ResultsDTO(contestants,totalVotes);
    }

}
