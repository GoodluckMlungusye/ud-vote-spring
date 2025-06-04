package com.goodamcodes.service;

import com.goodamcodes.dto.VoteDTO;
import com.goodamcodes.model.*;
import com.goodamcodes.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    private void checkForGeneralCategory(Contestant contestant, Category category, Student voter) {
        Category contestantCategory = contestant.getCategory();
        boolean hasValidCategory =  contestantCategory.equals(category);

        if (hasValidCategory && !category.getIsGeneral()) {
            College voterCollege = voter.getCollege();
            College contestantCollege = contestant.getStudent().getCollege();
            if (!voterCollege.equals(contestantCollege)) {
                throw new IllegalArgumentException(
                        "This election is specific for " + contestantCollege.getName().toUpperCase() + " students."
                );
            }
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
            throw new IllegalArgumentException("Voter has already voted in this category");
        }
    }

    public int countVotesInCategory(Long categoryId) {
        return voteRepository.findAllByCategoryId(categoryId).size();
    }

    public int countContestantVotesInCategory(Long contestantId, Long categoryId) {
        return voteRepository.findAllByContestantIdAndCategoryId(contestantId,categoryId).size();
    }

    public String getContestantVotePercent(Long contestantId, Long categoryId) {
        int totalVotes = countVotesInCategory(categoryId);
        int contestantVotes = countContestantVotesInCategory(contestantId, categoryId);
        if (totalVotes == 0) return "0.00%";
        double percentage = (contestantVotes * 100.0) / totalVotes;
        return String.format("%.2f%%", percentage);
    }

}
