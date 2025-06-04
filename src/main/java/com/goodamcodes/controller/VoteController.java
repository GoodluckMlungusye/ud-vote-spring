package com.goodamcodes.controller;

import com.goodamcodes.dto.VoteDTO;
import com.goodamcodes.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@PreAuthorize("hasRole('VOTER')")
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/vote")
    public ResponseEntity<String> castVote(@RequestBody VoteDTO vote){
        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.castVote(vote));
    }

    @GetMapping("/votes/{categoryId}")
    public ResponseEntity<Integer> countVotesInCategory(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.countVotesInCategory(categoryId));
    }

    @GetMapping("/votes/contestant/{contestantId}/category/{categoryId}")
    public ResponseEntity<Integer> countContestantVotesInCategory(@PathVariable("contestantId") Long contestantId, @PathVariable("categoryId") Long categoryId){
        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.countContestantVotesInCategory(contestantId,categoryId));
    }

    @GetMapping("/vote-percent/contestant/{contestantId}/category/{categoryId}")
    public ResponseEntity<String> getContestantVotePercent(@PathVariable("contestantId") Long contestantId, @PathVariable("categoryId") Long categoryId){
        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.getContestantVotePercent(contestantId,categoryId));
    }
}
