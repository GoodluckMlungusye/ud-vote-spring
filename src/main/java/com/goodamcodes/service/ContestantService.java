package com.goodamcodes.service;

import com.goodamcodes.dto.ContestantDTO;
import com.goodamcodes.dto.ContestantDetailsDTO;
import com.goodamcodes.mapper.ContestantMapper;
import com.goodamcodes.model.Category;
import com.goodamcodes.model.Contestant;
import com.goodamcodes.model.Student;
import com.goodamcodes.model.security.UserInfo;
import com.goodamcodes.repository.CategoryRepository;
import com.goodamcodes.repository.ContestantRepository;
import com.goodamcodes.repository.StudentRepository;
import com.goodamcodes.service.utility.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContestantService {

    private final ContestantRepository contestantRepository;
    private final StudentRepository studentRepository;
    private final CategoryRepository categoryRepository;
    private final ContestantMapper contestantMapper;
    private final FileService fileService;

    public String addContestant(ContestantDTO contestantDTO, MultipartFile file) {

        Student student = studentRepository.findById(contestantDTO.getStudentId()).orElseThrow(()-> new IllegalArgumentException("Student not found"));
        Category category = categoryRepository.findById(contestantDTO.getCategoryId()).orElseThrow(()-> new IllegalArgumentException("Category not found"));

        Optional<Contestant> existingContestant = contestantRepository.findByStudent(student);
        if (existingContestant.isPresent()) {
            throw new IllegalArgumentException("Contestant already exists");
        }

        Contestant contestant = contestantMapper.toContestant(contestantDTO);
        contestant.setStudent(student);
        contestant.setCategory(category);

        if(file != null && !file.isEmpty()) {
            String fileName = fileService.saveFile(file);
            contestant.setVideoUrl(fileName);
        }

        Contestant savedContestant = contestantRepository.save(contestant);
        return "Contestant " + savedContestant.getStudent().getUser().getFirstName() + " " + savedContestant.getStudent().getUser().getLastName() + " has been added successfully";
    }

    public List<ContestantDTO> fetchAllContestants() {
        List<Contestant> contestants = contestantRepository.findAll();
        return contestantMapper.toContestantDTOs(contestants);
    }

    public List<ContestantDTO> fetchAllContestantsByCategoryId(Long categoryId) {
        List<Contestant> contestants = contestantRepository.findByCategoryId(categoryId);
        return contestantMapper.toContestantDTOs(contestants);
    }

    public String updateContestant(Long contestantId, ContestantDTO contestantDTO, MultipartFile file){
        Contestant existingContestant = contestantRepository.findById(contestantId).orElseThrow(
                () -> new IllegalArgumentException("Contestant was not found")
        );

        contestantMapper.updateContestantFromDTO(contestantDTO, existingContestant);

        if(file != null && !file.isEmpty()) {
            fileService.deleteFile(existingContestant.getVideoUrl());
            String fileName = fileService.saveFile(file);
            existingContestant.setVideoUrl(fileName);
        }

        if(contestantDTO.getStudentId() != null && !contestantDTO.getStudentId().equals(existingContestant.getStudent().getId())) {
            Student newStudent = studentRepository.findById(contestantDTO.getStudentId()).orElseThrow(
                    () -> new IllegalArgumentException("Student does not exist")
            );
            existingContestant.setStudent(newStudent);
        }

        if(contestantDTO.getCategoryId() != null && !contestantDTO.getCategoryId().equals(existingContestant.getCategory().getId())) {
            Category newCategory = categoryRepository.findById(contestantDTO.getCategoryId()).orElseThrow(
                    () -> new IllegalArgumentException("Category does not exist")
            );
            existingContestant.setCategory(newCategory);
        }

        Contestant updatedContestant = contestantRepository.save(existingContestant);
        return "Contestant " + updatedContestant.getStudent().getUser().getFirstName() + " " + updatedContestant.getStudent().getUser().getLastName() + " has been updated successfully";
    }

    public String deleteContestant(Long contestantId){
        Contestant contestant = contestantRepository.findById(contestantId).orElseThrow(
                () -> new IllegalArgumentException("Contestant does not exist")
        );
        fileService.deleteFile(contestant.getVideoUrl());
        contestantRepository.deleteById(contestantId);
        return "Contestant " + contestant.getStudent().getUser().getFirstName() + " " + contestant.getStudent().getUser().getLastName() + " has been deleted";
    }

    public ContestantDetailsDTO getContestantDetails(Long contestantId){
        Student student = studentRepository.findById(contestantId).orElseThrow(()-> new IllegalArgumentException("Student associated with this contestant does not exist"));
        UserInfo user = student.getUser();

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String name = firstName + " " + lastName;
        String imageUrl = student.getImageUrl();
        String collegeName = student.getCollege().getName();

        return new ContestantDetailsDTO(name, imageUrl, collegeName);

    }
}
