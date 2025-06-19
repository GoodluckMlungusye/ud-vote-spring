package com.goodamcodes.service;

import com.goodamcodes.dto.ElectionDTO;
import com.goodamcodes.mapper.ElectionMapper;
import com.goodamcodes.model.Election;
import com.goodamcodes.repository.ElectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ElectionService {

    private final ElectionRepository electionRepository;
    private final ElectionMapper electionMapper;

    public String addElection(ElectionDTO electionDTO) {
        Optional<Election> existingElection = electionRepository.findByName(electionDTO.getName());
        if (existingElection.isPresent()) {
            throw new IllegalArgumentException("Election already exists");
        }

        Election election = electionMapper.toElection(electionDTO);

        Election savedElection = electionRepository.save(election);
        return "Election " + savedElection.getName() + " has been added successfully";
    }

    public List<ElectionDTO> fetchAllElections() {
        List<Election> elections = electionRepository.findAll();
        return electionMapper.toElectionDTOs(elections);
    }

    public String updateElection(Long electionId, ElectionDTO electionDTO){
        Election existingElection = electionRepository.findById(electionId).orElseThrow(
                () -> new IllegalArgumentException("Election " +  electionDTO.getName() + " was not found")
        );

        electionMapper.updateElectionFromDTO(electionDTO, existingElection);

        Election updatedElection = electionRepository.save(existingElection);
        return "Election " + updatedElection.getName() + " has been updated successfully";
    }

    public String deleteElection(Long electionId){
        Election election = electionRepository.findById(electionId).orElseThrow(
                () -> new IllegalArgumentException("Election does not exist")
        );
        electionRepository.deleteById(electionId);
        return "Election " + election.getName() + " has been deleted";
    }

}
