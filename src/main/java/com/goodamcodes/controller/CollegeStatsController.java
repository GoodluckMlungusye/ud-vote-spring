package com.goodamcodes.controller;

import com.goodamcodes.dto.*;
import com.goodamcodes.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/college")
@RequiredArgsConstructor
public class CollegeStatsController {

    private final StudentService studentService;
    private final CollegeService collegeService;

    @GetMapping(path = "/rating/{collegeId}/{year}")
    public ResponseEntity<String> getCollegeParticipationRating(@PathVariable("collegeId") Long collegeId, @PathVariable("year") Integer year){
        return ResponseEntity.status(HttpStatus.OK).body(collegeService.getCollegeParticipationRatingPerYear(collegeId, year));
    }

    @GetMapping("/voted/{collegeId}/{year}")
    public ResponseEntity<List<StudentDTO>> getStudentsWhoVotedByCollegeAndYear(@PathVariable("collegeId") Long collegeId, @PathVariable("year") Integer year){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudentsWhoVotedByCollegeAndYear(collegeId, year));
    }

    @GetMapping("/{collegeId}/{year}")
    public ResponseEntity<List<StudentDTO>> getStudentsByCollegeAndYear(@PathVariable("collegeId") Long collegeId, @PathVariable("year") Integer year){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudentsByCollegeAndYear(collegeId, year));
    }
}
