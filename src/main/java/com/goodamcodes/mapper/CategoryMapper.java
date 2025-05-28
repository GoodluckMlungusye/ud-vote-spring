package com.goodamcodes.mapper;

import com.goodamcodes.dto.CategoryDTO;
import com.goodamcodes.model.Category;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toCategoryDTO(Category category);
    Category toCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> toCategoryDTOs(List<Category> categories);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategoryFromDTO(CategoryDTO categoryDTO, @MappingTarget Category category);
}
