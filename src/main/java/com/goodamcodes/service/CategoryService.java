package com.goodamcodes.service;

import com.goodamcodes.dto.CategoryDTO;
import com.goodamcodes.mapper.CategoryMapper;
import com.goodamcodes.model.Category;
import com.goodamcodes.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public String addCategory(CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = categoryRepository.findByName(categoryDTO.getName());
        if (existingCategory.isPresent()) {
            throw new IllegalStateException("Category already exists");
        }

        Category category = categoryMapper.toCategory(categoryDTO);

        Category savedCategory = categoryRepository.save(category);
        return "Category " + savedCategory.getName() + " has been added successfully";
    }

    public List<CategoryDTO> fetchAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toCategoryDTOs(categories);
    }

    public String updateCategory(Long categoryId, CategoryDTO categoryDTO){
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalStateException("Category " +  categoryDTO.getName() + " was not found")
        );

        categoryMapper.updateCategoryFromDTO(categoryDTO, existingCategory);

        Category updatedCategory = categoryRepository.save(existingCategory);
        return "Category " + updatedCategory.getName() + " has been updated successfully";
    }

    public String deleteCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalStateException("Category does not exist")
        );
        categoryRepository.deleteById(categoryId);
        return "Category " + category.getName() + " has been deleted";
    }
}
