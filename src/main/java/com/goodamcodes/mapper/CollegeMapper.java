package com.goodamcodes.mapper;

import com.goodamcodes.dto.college.CollegeDTO;
import com.goodamcodes.dto.college.CollegeResponseDTO;
import com.goodamcodes.model.College;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CollegeMapper {

    College toCollege(CollegeDTO collegeDTO);
    List<CollegeDTO> toCollegeDTOs(List<College> colleges);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCollegeFromDTO(CollegeDTO collegeDTO,@MappingTarget College college);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "imageUrl", target = "imageUrl")
    @Mapping(source = "electionYear", target = "electionYear")
    @Mapping(target = "registeredStudents", ignore = true)
    @Mapping(target = "votedStudents", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "percentage", ignore = true)
    CollegeResponseDTO toResponseDTO(College college);

}
