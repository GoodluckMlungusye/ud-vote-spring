package com.goodamcodes.controller;

import com.goodamcodes.dto.*;
import com.goodamcodes.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/student")
@RequiredArgsConstructor
@PreAuthorize("hasRole('VOTER')")
public class StatisticsController {

    private final StudentService studentService;
    private final ContestantService contestantService;
    private final VoteService voteService;
    private final CollegeService collegeService;

    @GetMapping("/{collegeId}/{year}")
    public ResponseEntity<List<StudentDTO>> getStudentsByCollegeAndYear(@PathVariable("collegeId") Long collegeId, @PathVariable("year") Integer year){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudentsByCollegeAndYear(collegeId, year));
    }

    @GetMapping("/voted/{collegeId}/{year}")
    public ResponseEntity<List<StudentDTO>> getStudentsWhoVotedByCollegeAndYear(@PathVariable("collegeId") Long collegeId, @PathVariable("year") Integer year){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudentsWhoVotedByCollegeAndYear(collegeId, year));
    }

    @GetMapping("/contestants/{categoryId}")
    public ResponseEntity<List<ContestantDTO>> fetchAllContestantsByCategoryId(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(contestantService.fetchAllContestantsByCategoryId(categoryId));
    }

    @GetMapping("/votes/{categoryId}")
    public ResponseEntity<Integer> countVotesInCategory(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(voteService.countVotesInCategory(categoryId));
    }

    @GetMapping("/votes/contestant/{contestantId}/category/{categoryId}")
    public ResponseEntity<Integer> countContestantVotesInCategory(@PathVariable("contestantId") Long contestantId, @PathVariable("categoryId") Long categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(voteService.countContestantVotesInCategory(contestantId,categoryId));
    }

    @GetMapping("/vote-percent/contestant/{contestantId}/category/{categoryId}")
    public ResponseEntity<String> getContestantVotePercent(@PathVariable("contestantId") Long contestantId, @PathVariable("categoryId") Long categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(voteService.getContestantVotePercent(contestantId,categoryId));
    }

    @GetMapping(path = "/rating/{collegeId}/{year}")
    public ResponseEntity<String> getCollegeParticipationRating(@PathVariable("collegeId") Long collegeId, @PathVariable("year") Integer year){
        return ResponseEntity.status(HttpStatus.OK).body(collegeService.getCollegeParticipationRatingPerYear(collegeId, year));
    }

}
