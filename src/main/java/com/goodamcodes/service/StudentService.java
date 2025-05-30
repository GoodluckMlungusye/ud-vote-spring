package com.goodamcodes.service;

import com.goodamcodes.dto.StudentDTO;
import com.goodamcodes.enums.Role;
import com.goodamcodes.mapper.StudentMapper;
import com.goodamcodes.model.College;
import com.goodamcodes.model.Student;
import com.goodamcodes.model.security.UserInfo;
import com.goodamcodes.repository.CollegeRepository;
import com.goodamcodes.repository.StudentRepository;
import com.goodamcodes.repository.security.UserInfoRepository;
import com.goodamcodes.service.security.UserInfoService;
import com.goodamcodes.service.utility.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CollegeRepository collegeRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserInfoService userInfoService;
    private final StudentMapper studentMapper;
    private final FileService fileService;

    public String registerStudent(StudentDTO studentDTO, MultipartFile file) {
        Optional<Student> existingStudent = studentRepository.findByRegistrationNumber(studentDTO.getRegistrationNumber());
        if (existingStudent.isPresent()) {
            throw new IllegalStateException("Student already exists");
        }

        College college = collegeRepository.findById(studentDTO.getCollegeId())
                .orElseThrow(() -> new IllegalStateException("College not found"));
        UserInfo user = userInfoRepository.findById(studentDTO.getUserId())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        userInfoService.addNewRoleToUser(user, Role.VOTER);

        Student student = studentMapper.toStudent(studentDTO);
        student.setCollege(college);
        student.setUser(user);

        if(file != null && !file.isEmpty()) {
            String fileName = fileService.saveFile(file);
            student.setImageUrl(fileName);
        }

        Student savedStudent = studentRepository.save(student);
        return "Student " + savedStudent.getRegistrationNumber() + " has been registered successfully";
    }

    public List<StudentDTO> getStudents(){
        List<Student> students = studentRepository.findAll();
        return studentMapper.toStudentDTOs(students);
    }

    public String updateStudent(Long studentId, StudentDTO studentDTO, MultipartFile file){
        Student existingStudent = studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalStateException("Student " +  studentDTO.getRegistrationNumber() + " was not found")
        );

        studentMapper.updateStudentFromDTO(studentDTO, existingStudent);

        if(file != null && !file.isEmpty()) {
            fileService.deleteFile(existingStudent.getImageUrl());
            String fileName = fileService.saveFile(file);
            existingStudent.setImageUrl(fileName);
        }

        if(studentDTO.getCollegeId() != null && !studentDTO.getCollegeId().equals(existingStudent.getCollege().getId())) {
            College newCollege = collegeRepository.findById(studentDTO.getCollegeId()).orElseThrow(
                    () -> new IllegalStateException("College does not exist")
            );
            existingStudent.setCollege(newCollege);
        }

        if(studentDTO.getUserId() != null && !studentDTO.getUserId().equals(existingStudent.getUser().getId())) {
            UserInfo newUser = userInfoRepository.findById(studentDTO.getUserId()).orElseThrow(
                    () -> new IllegalStateException("User does not exist")
            );
            existingStudent.setUser(newUser);
        }

        Student updatedStudent = studentRepository.save(existingStudent);
        return "Student " + updatedStudent.getRegistrationNumber() + " has been updated successfully";
    }


    public String deleteStudent(Long studentId){
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalStateException("Student does not exist")
        );
        fileService.deleteFile(student.getImageUrl());
        studentRepository.deleteById(studentId);
        return "Student with registration number " + student.getRegistrationNumber() + " has been deleted";
    }
}
