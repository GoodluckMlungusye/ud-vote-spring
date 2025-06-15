package com.goodamcodes.controller;

import com.goodamcodes.dto.CategoryDTO;
import com.goodamcodes.dto.ElectionDTO;
import com.goodamcodes.dto.StudentDTO;
import com.goodamcodes.dto.security.*;
import com.goodamcodes.service.CategoryService;
import com.goodamcodes.service.ElectionService;
import com.goodamcodes.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final CategoryService categoryService;
    private final ElectionService electionService;

    @PostMapping
    public ResponseEntity<String> registerStudent(@RequestPart("student") StudentDTO studentDTO, @RequestPart("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.registerStudent(studentDTO, file));
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getStudents(){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudents());
    }

    @GetMapping("/{collegeId}/{year}")
    public ResponseEntity<List<StudentDTO>> getStudentsByCollegeAndYear(@PathVariable("collegeId") Long collegeId, @PathVariable("year") Integer year){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudentsByCollegeAndYear(collegeId, year));
    }

    @GetMapping("/voted/{collegeId}/{year}")
    public ResponseEntity<List<StudentDTO>> getStudentsWhoVotedByCollegeAndYear(@PathVariable("collegeId") Long collegeId, @PathVariable("year") Integer year){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudentsWhoVotedByCollegeAndYear(collegeId, year));
    }

    @PatchMapping(path = "/{studentId}")
    public ResponseEntity<String> updateStudent(@PathVariable("studentId") Long studentId, @RequestPart("student") StudentDTO studentDTO, @RequestPart("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.updateStudent(studentId, studentDTO, file));
    }

    @DeleteMapping(path = "/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("studentId") Long studentId){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.deleteStudent(studentId));
    }

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

    @GetMapping(path = "/categories/{electionId}")
    public ResponseEntity<List<CategoryDTO>> getVotingCategories(@PathVariable("electionId") Long electionId){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.fetchAllCategoriesByElection(electionId));
    }

}
