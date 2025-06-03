package com.goodamcodes.controller;

import com.goodamcodes.dto.StudentDTO;
import com.goodamcodes.dto.security.*;
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

    @PostMapping
    public ResponseEntity<String> registerStudent(@RequestPart("student") StudentDTO studentDTO, @RequestPart("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.registerStudent(studentDTO, file));
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getStudents(){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudents());
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
    public ResponseEntity<String> requestOTP(@RequestBody OTPRequestDTO otp) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.requestOTP(otp));
    }

    @PatchMapping("/access")
    public ResponseEntity<String> getVotingAccess(@RequestBody ConfirmOTPCodeDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getVotingAccess(request));
    }

}
