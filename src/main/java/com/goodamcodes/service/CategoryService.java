package com.goodamcodes.service;

import com.goodamcodes.dto.CategoryDTO;
import com.goodamcodes.mapper.CategoryMapper;
import com.goodamcodes.model.Category;
import com.goodamcodes.model.Election;
import com.goodamcodes.repository.CategoryRepository;
import com.goodamcodes.repository.ElectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ElectionRepository electionRepository;
    private final CategoryMapper categoryMapper;

    public String addCategory(CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = categoryRepository.findByName(categoryDTO.getName());
        if (existingCategory.isPresent()) {
            throw new IllegalArgumentException("Category already exists");
        }

        Category category = categoryMapper.toCategory(categoryDTO);

        Election election = electionRepository.findById(categoryDTO.getElectionId())
                .orElseThrow(() -> new IllegalArgumentException("Election not found"));

        category.setElection(election);

        Category savedCategory = categoryRepository.save(category);
        return "Category " + savedCategory.getName() + " has been added successfully";
    }

    public List<CategoryDTO> fetchAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toCategoryDTOs(categories);
    }

    public List<CategoryDTO> fetchAllCategoriesByElection(Long electionId) {
        List<Category> categories = categoryRepository.findByElectionId(electionId);
        if (categories.isEmpty()) {
            throw new IllegalArgumentException("No categories found for this election");
        }
        return categoryMapper.toCategoryDTOs(categories);
    }

    public String updateCategory(Long categoryId, CategoryDTO categoryDTO){
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalArgumentException("Category " +  categoryDTO.getName() + " was not found")
        );

        categoryMapper.updateCategoryFromDTO(categoryDTO, existingCategory);

        Category updatedCategory = categoryRepository.save(existingCategory);
        return "Category " + updatedCategory.getName() + " has been updated successfully";
    }

    public String deleteCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalArgumentException("Category does not exist")
        );
        categoryRepository.deleteById(categoryId);
        return "Category " + category.getName() + " has been deleted";
    }

}
