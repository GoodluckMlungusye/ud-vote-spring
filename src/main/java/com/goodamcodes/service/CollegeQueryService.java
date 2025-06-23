package com.goodamcodes.service;

import com.goodamcodes.dto.college.CollegeResponseDTO;
import com.goodamcodes.mapper.CollegeMapper;
import com.goodamcodes.model.College;
import com.goodamcodes.repository.CollegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollegeQueryService {

    private final CollegeMapper collegeMapper;
    private final StudentService studentService;
    private final CollegeRepository collegeRepository;

    public List<CollegeResponseDTO> getCollegesStats(int year) {

        List<College> colleges = collegeRepository.findAll();

        return colleges.stream()
                .map(college -> getCollegeResponse(college, year))
                .collect(Collectors.toList());
    }

    public CollegeResponseDTO getCollegeStats(Long collegeId, int year) {
        College existingCollege = collegeRepository.findById(collegeId).orElseThrow(() -> new IllegalArgumentException("College not found"));
        College college =  collegeRepository.findByNameAndElectionYear(existingCollege.getName(),year).orElseThrow(() ->
                new IllegalArgumentException("College not registered for this election year"));

        return getCollegeResponse(college, year);
    }

    public CollegeResponseDTO getCollegeResponse(College college, int year) {

        CollegeResponseDTO dto = collegeMapper.toResponseDTO(college);

        int registeredStudents = studentService.getStudentsByCollegeAndYear(college.getId(),year).size();
        int votedStudents = studentService.getStudentsWhoVotedByCollegeAndYear(college.getId(),year).size();

        int rating = getRateValue(votedStudents, registeredStudents);
        String percentage = getPercentValue(votedStudents, registeredStudents);

        dto.setRegisteredStudents(registeredStudents);
        dto.setVotedStudents(votedStudents);
        dto.setRating(rating);
        dto.setPercentage(percentage);

        return dto;
    }

    private double getPercentRate(int votedStudents, int registeredStudents) {
        return  (votedStudents * 100.0) /registeredStudents;
    }

    private int getRateValue(int votedStudents, int registeredStudents) {
        double value = getPercentRate(votedStudents, registeredStudents);
        int bucket = (int) (value / 20);

        return switch (bucket) {
            case 5, 4 -> 5;
            case 3 -> 4;
            case 2 -> 3;
            case 1 -> 2;
            default -> 1;
        };
    }

    private String getPercentValue( int votedStudents, int registeredStudents) {
        if (registeredStudents == 0) return "0.00%";
        double percentage = getPercentRate(votedStudents, registeredStudents);
        return String.format("%.2f%%", percentage);
    }
}
