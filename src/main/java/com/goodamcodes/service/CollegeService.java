package com.goodamcodes.service;

import com.goodamcodes.dto.college.CollegeDTO;
import com.goodamcodes.mapper.CollegeMapper;
import com.goodamcodes.model.College;
import com.goodamcodes.repository.CollegeRepository;
import com.goodamcodes.service.utility.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollegeService {

    private final CollegeRepository collegeRepository;
    private final CollegeMapper collegeMapper;
    private final FileService fileService;

    public String addCollege(CollegeDTO collegeDTO, MultipartFile file) {
        Optional<College> existingCollege = collegeRepository.findByNameAndElectionYear(collegeDTO.getName(),  collegeDTO.getElectionYear());
        if (existingCollege.isPresent()) {
            throw new IllegalArgumentException("College already exists");
        }
        College college = collegeMapper.toCollege(collegeDTO);

        if(file != null && !file.isEmpty()) {
            String fileName = fileService.saveFile(file);
            college.setImageUrl(fileName);
        }
        College savedCollege = collegeRepository.save(college);
        return "College " + savedCollege.getName() + " has been added successfully";
    }

    public List<CollegeDTO> fetchAllColleges() {
        List<College> colleges = collegeRepository.findAll();
        return collegeMapper.toCollegeDTOs(colleges);
    }

    public String updateCollege(Long collegeId, CollegeDTO collegeDTO, MultipartFile file){
        College existingCollege = collegeRepository.findById(collegeId).orElseThrow(
                () -> new IllegalArgumentException("College " +  collegeDTO.getName() + " was not found")
        );

        collegeMapper.updateCollegeFromDTO(collegeDTO, existingCollege);

        if(file != null && !file.isEmpty()) {
            fileService.deleteFile(existingCollege.getImageUrl());
            String fileName = fileService.saveFile(file);
            existingCollege.setImageUrl(fileName);
        }

        College updatedCollege = collegeRepository.save(existingCollege);
        return "College " + updatedCollege.getName() + " has been updated successfully";
    }

    public String deleteCollege(Long collegeId){
        College college = collegeRepository.findById(collegeId).orElseThrow(
                () -> new IllegalArgumentException("College does not exist")
        );
        fileService.deleteFile(college.getImageUrl());
        collegeRepository.deleteById(collegeId);
        return "College " + college.getName() + " has been deleted";
    }

}
