package com.goodamcodes.service;

import com.goodamcodes.dto.VoteDTO;
import com.goodamcodes.model.*;
import com.goodamcodes.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final ContestantRepository contestantRepository;
    private final StudentRepository studentRepository;
    private final ElectionRepository electionRepository;
    private final CategoryRepository categoryRepository;

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

        checkForGeneralCategory(contestant, category, voter);

        boolean hasVoted = voteRepository
                .existsByVoterAndCategoryAndElection(voter, category, election);
        if (hasVoted) {
            throw new IllegalStateException("Voter has already voted in this category");
        }

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
        Instant now = Instant.now();

        if (now.isBefore(Instant.from(election.getStartTime()))) {
            throw new IllegalStateException("The election has not started yet");
        }
        if (now.isAfter(Instant.from(election.getEndTime()))) {
            throw new IllegalStateException("The election has already ended");
        }
    }

    private void checkVoterEligibilityByYear(Election election, Student voter) {
        int electionYear = LocalDateTime.ofInstant(Instant.from(election.getStartTime()), ZoneId.systemDefault())
                .getYear();

        if (voter.getElectionYear() != electionYear) {
            throw new IllegalStateException(
                    "Voter is not eligible: registration year " +
                            voter.getElectionYear() + " does not match election year " + electionYear
            );
        }
    }

    private void checkForGeneralCategory(Contestant contestant, Category category, Student voter) {
        Category contestantCategory = contestant.getCategory();
        boolean hasValidCategory =  contestantCategory.equals(category);

        if (hasValidCategory && !category.getIsGeneral()) {
            College voterCollege = voter.getCollege();
            College contestantCollege = contestant.getStudent().getCollege();
            if (!voterCollege.equals(contestantCollege)) {
                throw new IllegalStateException(
                        "Voter’s college (" + voterCollege.getName() +
                                ") is not eligible to vote for this category; " +
                                "only students from " + contestantCollege.getName() +
                                " may vote for \"" + category.getName() + "\"."
                );
            }
        }
    }

}
