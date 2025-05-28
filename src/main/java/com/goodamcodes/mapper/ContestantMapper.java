package com.goodamcodes.mapper;

import com.goodamcodes.dto.ContestantDTO;
import com.goodamcodes.model.Contestant;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContestantMapper {

    @Mapping(target = "student", ignore = true)
    @Mapping(target = "category", ignore = true)
    Contestant toContestant(ContestantDTO contestantDTO);

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "categoryId", source = "category.id")
    ContestantDTO toContestantDTO(Contestant contestant);

    List<ContestantDTO> toContestantDTOs(List<Contestant> contestants);
    List<Contestant> toContestants(List<ContestantDTO> contestantDTOS);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateContestantFromDTO(ContestantDTO contestantDTO, @MappingTarget Contestant contestant);
}
