package com.goodamcodes.service;

import com.goodamcodes.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteQueryService {

    private final VoteRepository voteRepository;

    public int countVotesInCategory(Long categoryId) {
        return voteRepository.findAllByCategoryId(categoryId).size();
    }

    public int countContestantVotesInCategory(Long contestantId, Long categoryId) {
        return voteRepository.findAllByContestantIdAndCategoryId(contestantId, categoryId).size();
    }

    public String getContestantVotePercent(Long contestantId, Long categoryId) {
        int totalVotes = countVotesInCategory(categoryId);
        int contestantVotes = countContestantVotesInCategory(contestantId, categoryId);
        if (totalVotes == 0) return "0.00%";
        double percentage = (contestantVotes * 100.0) / totalVotes;
        return String.format("%.2f%%", percentage);
    }

}
