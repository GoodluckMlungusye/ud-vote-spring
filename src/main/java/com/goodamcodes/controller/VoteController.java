package com.goodamcodes.controller;

import com.goodamcodes.dto.VoteDTO;
import com.goodamcodes.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
