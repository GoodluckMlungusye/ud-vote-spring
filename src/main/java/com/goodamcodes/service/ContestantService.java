package com.goodamcodes.service;

import com.goodamcodes.dto.ContestantDTO;
import com.goodamcodes.mapper.ContestantMapper;
import com.goodamcodes.model.Contestant;
import com.goodamcodes.model.Student;
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
    private final ContestantMapper contestantMapper;
    private final FileService fileService;

    public String addContestant(ContestantDTO contestantDTO, MultipartFile file) {
        Student contestant = studentRepository.findById(contestantDTO.getStudentId()).orElseThrow(()-> new IllegalArgumentException("Contestant not found"));
        Optional<Contestant> existingContestant = contestantRepository.findByStudent(contestant);
        if (existingContestant.isPresent()) {
            throw new IllegalStateException("Contestant already exists");
        }
        Contestant newContestant = contestantMapper.toContestant(contestantDTO);

        if(file != null && !file.isEmpty()) {
            String fileName = fileService.saveFile(file);
            newContestant.setVideoUrl(fileName);
        }
        Contestant savedContestant = contestantRepository.save(newContestant);
        return "Contestant " + savedContestant.getStudent().getUser().getFirstName() + " " + savedContestant.getStudent().getUser().getLastName() + " has been added successfully";
    }

    public List<ContestantDTO> fetchAllContestants() {
        List<Contestant> contestants = contestantRepository.findAll();
        return contestantMapper.toContestantDTOs(contestants);
    }

    public String updateContestant(Long contestantId, ContestantDTO contestantDTO, MultipartFile file){
        Contestant existingContestant = contestantRepository.findById(contestantId).orElseThrow(
                () -> new IllegalStateException("Contestant was not found")
        );

        contestantMapper.updateContestantFromDTO(contestantDTO, existingContestant);

        if(file != null && !file.isEmpty()) {
            fileService.deleteFile(existingContestant.getVideoUrl());
            String fileName = fileService.saveFile(file);
            existingContestant.setVideoUrl(fileName);
        }

        Contestant updatedContestant = contestantRepository.save(existingContestant);
        return "Contestant " + updatedContestant.getStudent().getUser().getFirstName() + " " + updatedContestant.getStudent().getUser().getLastName() + " has been updated successfully";
    }

    public String deleteContestant(Long contestantId){
        Contestant contestant = contestantRepository.findById(contestantId).orElseThrow(
                () -> new IllegalStateException("Contestant does not exist")
        );
        fileService.deleteFile(contestant.getVideoUrl());
        contestantRepository.deleteById(contestantId);
        return "Contestant " + contestant.getStudent().getUser().getFirstName() + " " + contestant.getStudent().getUser().getLastName() + " has been deleted";
    }
}
