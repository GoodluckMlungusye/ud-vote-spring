package com.goodamcodes.controller;

import com.goodamcodes.dto.CollegeDTO;
import com.goodamcodes.service.CollegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/college")
@RequiredArgsConstructor
public class CollegeController {

    private final CollegeService collegeService;

    @PostMapping
    public ResponseEntity<String> addCollege(@RequestPart("college") CollegeDTO collegeDTO, @RequestPart("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(collegeService.addCollege(collegeDTO, file));
    }

    @GetMapping
    public ResponseEntity<List<CollegeDTO>> fetchAllColleges(){
        return ResponseEntity.status(HttpStatus.OK).body(collegeService.fetchAllColleges());
    }

    @PatchMapping(path = "/{collegeId}")
    public ResponseEntity<String> updateCollege(@PathVariable("collegeId") Long collegeId, @RequestPart("college") CollegeDTO collegeDTO, @RequestPart("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(collegeService.updateCollege(collegeId, collegeDTO, file));
    }

    @DeleteMapping(path = "/{collegeId}")
    public ResponseEntity<String> deleteCollege(@PathVariable("collegeId") Long collegeId){
        return ResponseEntity.status(HttpStatus.OK).body(collegeService.deleteCollege(collegeId));
    }
}
