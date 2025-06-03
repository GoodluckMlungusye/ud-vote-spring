package com.goodamcodes.mapper;

import com.goodamcodes.dto.ElectionDTO;
import com.goodamcodes.model.Election;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface ElectionMapper {

    Election toElection(ElectionDTO electionDTO);
    ElectionDTO toElectionDTO(Election election);
    List<ElectionDTO> toElectionDTOs(List<Election> elections);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateElectionFromDTO(ElectionDTO electionDTO, @MappingTarget Election election);

}
