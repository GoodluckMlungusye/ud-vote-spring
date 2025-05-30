package com.goodamcodes.controller;

import com.goodamcodes.dto.ElectionDTO;
import com.goodamcodes.service.ElectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/election")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ELECTION_MANAGER')")
public class ElectionController {

    private final ElectionService electionService;

    @PostMapping
    public ResponseEntity<String> addElection(@RequestBody ElectionDTO election){
        return ResponseEntity.status(HttpStatus.CREATED).body(electionService.addElection(election));
    }

    @GetMapping
    public ResponseEntity<List<ElectionDTO>> fetchAllElections(){
        return ResponseEntity.status(HttpStatus.OK).body(electionService.fetchAllElections());
    }

    @PatchMapping(path = "/{electionId}")
    public ResponseEntity<String> updateElection(@PathVariable("electionId") Long electionId, @RequestBody ElectionDTO election){
        return ResponseEntity.status(HttpStatus.OK).body(electionService.updateElection(electionId, election));
    }

    @DeleteMapping(path = "/{electionId}")
    public ResponseEntity<String> deleteElection(@PathVariable("electionId") Long electionId){
        return ResponseEntity.status(HttpStatus.OK).body(electionService.deleteElection(electionId));
    }
}
