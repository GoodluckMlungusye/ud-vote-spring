package com.goodamcodes.controller;

import com.goodamcodes.dto.college.CollegeResponseDTO;
import com.goodamcodes.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/college")
@RequiredArgsConstructor
public class CollegeStatsController {

    private final CollegeQueryService collegeQueryService;

    @GetMapping("/year/{electionYear}")
    public ResponseEntity<List<CollegeResponseDTO>> getCollegesStats(@PathVariable int electionYear) {
        return ResponseEntity.ok(collegeQueryService.getCollegesStats(electionYear));
    }

    @GetMapping("/{collegeId}/year/{electionYear}")
    public ResponseEntity<CollegeResponseDTO> getCollegeStats(@PathVariable Long collegeId, @PathVariable int electionYear) {
        return ResponseEntity.ok(collegeQueryService.getCollegeStats(collegeId, electionYear));
    }
}
