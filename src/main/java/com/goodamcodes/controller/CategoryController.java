package com.goodamcodes.controller;

import com.goodamcodes.dto.CategoryDTO;
import com.goodamcodes.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/election/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> addCategory(@RequestBody CategoryDTO category){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.addCategory(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> fetchAllCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.fetchAllCategories());
    }

    @GetMapping(path = "/{electionId}")
    public ResponseEntity<List<CategoryDTO>> fetchAllCategoriesByElection(@PathVariable("electionId") Long electionId){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.fetchAllCategoriesByElection(electionId));
    }

    @PatchMapping(path = "/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody CategoryDTO category){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(categoryId, category));
    }

    @DeleteMapping(path = "/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.deleteCategory(categoryId));
    }
}
