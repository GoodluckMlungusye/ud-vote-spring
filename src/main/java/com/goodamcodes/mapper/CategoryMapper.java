package com.goodamcodes.mapper;

import com.goodamcodes.dto.CategoryDTO;
import com.goodamcodes.model.Category;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "election", ignore = true)
    Category toCategory(CategoryDTO categoryDTO);
    @Mapping(target = "electionId", source = "election.id")
    List<CategoryDTO> toCategoryDTOs(List<Category> categories);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategoryFromDTO(CategoryDTO categoryDTO, @MappingTarget Category category);
}
