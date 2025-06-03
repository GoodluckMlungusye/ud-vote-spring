package com.goodamcodes.controller;

import com.goodamcodes.dto.ContestantDTO;
import com.goodamcodes.service.ContestantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/election/contestant")
@RequiredArgsConstructor
public class ContestantController {

    private final ContestantService contestantService;

    @PostMapping
    public ResponseEntity<String> addContestant(@RequestPart("contestant") ContestantDTO contestantDTO, @RequestPart("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(contestantService.addContestant(contestantDTO, file));
    }

    @GetMapping
    public ResponseEntity<List<ContestantDTO>> fetchAllContestants(){
        return ResponseEntity.status(HttpStatus.OK).body(contestantService.fetchAllContestants());
    }

    @PatchMapping(path = "/{contestantId}")
    public ResponseEntity<String> updateContestant(@PathVariable("contestantId") Long contestantId, @RequestPart("contestant") ContestantDTO contestantDTO, @RequestPart("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(contestantService.updateContestant(contestantId, contestantDTO, file));
    }

    @DeleteMapping(path = "/{contestantId}")
    public ResponseEntity<String> deleteContestant(@PathVariable("contestantId") Long contestantId){
        return ResponseEntity.status(HttpStatus.OK).body(contestantService.deleteContestant(contestantId));
    }
}
