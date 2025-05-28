package com.goodamcodes.mapper;

import com.goodamcodes.dto.CollegeDTO;
import com.goodamcodes.model.College;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CollegeMapper {

    College toCollege(CollegeDTO collegeDTO);
    CollegeDTO toCollegeDTO(College college);
    List<CollegeDTO> toCollegeDTOs(List<College> colleges);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCollegeFromDTO(CollegeDTO collegeDTO,@MappingTarget College college);

}
