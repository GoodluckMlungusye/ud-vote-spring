package com.goodamcodes.controller;

import com.goodamcodes.dto.*;
import com.goodamcodes.dto.security.ConfirmOTPCodeDTO;
import com.goodamcodes.dto.security.OTPRequestDTO;
import com.goodamcodes.dto.security.OTPResponseDTO;
import com.goodamcodes.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/student")
@RequiredArgsConstructor
public class VoterController {

    private final StudentService studentService;
    private final ElectionService electionService;
    private final VoteService voteService;

    @PostMapping("/otp")
    public ResponseEntity<OTPResponseDTO> requestOTP(@RequestBody OTPRequestDTO otp) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.requestOTP(otp));
    }

    @PatchMapping("/access")
    public ResponseEntity<String> getVotingAccess(@RequestBody ConfirmOTPCodeDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getVotingAccess(request));
    }

    @GetMapping("/elections")
    public ResponseEntity<List<ElectionDTO>> getElectionTypes(){
        return ResponseEntity.status(HttpStatus.OK).body(electionService.fetchAllElections());
    }

    @PostMapping("/vote")
    public ResponseEntity<String> castVote(@RequestBody VoteDTO vote){
        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.castVote(vote));
    }

    @GetMapping("/vote-status/category/{categoryId}/voter/{voterId}")
    public ResponseEntity<Boolean> hasVoted(@PathVariable("categoryId") Long categoryId, @PathVariable("voterId") Long voterId){
        return ResponseEntity.status(HttpStatus.OK).body(voteService.hasVoted(categoryId, voterId));
    }

    @GetMapping("/results/{categoryId}")
    public ResponseEntity<ResultsDTO> getCategoryResults(@PathVariable Long categoryId) {
        return ResponseEntity.ok(voteService.getCategoryResults(categoryId));
    }
}
