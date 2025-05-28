package com.goodamcodes.mapper;

import com.goodamcodes.dto.StudentDTO;
import com.goodamcodes.model.Student;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "college", ignore = true)
    Student toStudent(StudentDTO studentDTO);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "collegeId", source = "college.id")
    StudentDTO toStudentDTO(Student student);

    List<StudentDTO> toStudentDTOs(List<Student> students);
    List<Student> toStudents(List<StudentDTO> studentDTOS);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentFromDTO(StudentDTO studentDTO, @MappingTarget Student student);
}
