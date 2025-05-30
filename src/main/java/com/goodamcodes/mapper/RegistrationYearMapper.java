package com.goodamcodes.mapper;

import com.goodamcodes.dto.RegistrationYearDTO;
import com.goodamcodes.model.RegistrationYear;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegistrationYearMapper {

    @Mapping(target = "registeredStudents", ignore = true)
    @Mapping(target = "votedStudents", ignore = true)
    RegistrationYear toRegistrationYear(RegistrationYearDTO registrationYearDTO);

    @Mapping(target = "registeredStudents", source = "registeredStudents")
    @Mapping(target = "votedStudents", source = "votedStudents")
    RegistrationYearDTO toRegistrationYearDTO(RegistrationYear registrationYear);

    List<RegistrationYearDTO> toRegistrationYearDTOs(List<RegistrationYear> registrationYears);
    List<RegistrationYear> toRegistrationYears(List<RegistrationYearDTO> registrationYearDTOS);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRegistrationYearFromDTO(RegistrationYearDTO registrationYearDTO, @MappingTarget RegistrationYear registrationYear);

}
