package com.goodamcodes.mapper;

import com.goodamcodes.dto.contestant.ContestantDTO;
import com.goodamcodes.dto.contestant.ContestantResponseDTO;
import com.goodamcodes.model.Contestant;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContestantMapper {

    @Mapping(target = "student", ignore = true)
    @Mapping(target = "category", ignore = true)
    Contestant toContestant(ContestantDTO dto);

    List<ContestantDTO> toContestantDTOs(List<Contestant> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateContestantFromDTO(ContestantDTO dto, @MappingTarget Contestant contestant);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "slogan", target = "slogan")
    @Mapping(source = "videoUrl", target = "videoUrl")
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "collegeName", ignore = true)
    @Mapping(target = "votes", ignore = true)
    @Mapping(target = "percentage", ignore = true)
    ContestantResponseDTO toResponseDTO(Contestant contestant);
}

