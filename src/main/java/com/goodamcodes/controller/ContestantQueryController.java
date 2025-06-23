package com.goodamcodes.controller;

import com.goodamcodes.dto.contestant.ContestantResponseDTO;
import com.goodamcodes.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ContestantQueryController {

    private final ContestantQueryService contestantQueryService;

    @GetMapping("/contestants/category/{categoryId}")
    public ResponseEntity<List<ContestantResponseDTO>> getContestants(@PathVariable Long categoryId) {
        return ResponseEntity.ok(contestantQueryService.getContestants(categoryId));
    }

    @GetMapping("/contestant/category/{categoryId}")
    public ResponseEntity<ContestantResponseDTO> getContestant(@PathVariable Long categoryId) {
        return ResponseEntity.ok(contestantQueryService.getContestant(categoryId));
    }
}
