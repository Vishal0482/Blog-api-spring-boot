package com.vishal.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.blog.payloads.CategoryDTO;
import com.vishal.blog.payloads.ResponseHandler;
import com.vishal.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<Object> createCategory(@Valid @RequestBody CategoryDTO categoryDto) {
		CategoryDTO createdCategoryDto = this.categoryService.createCategory(categoryDto);
		return ResponseHandler.generateResponse("Category Created Successfully", HttpStatus.OK, createdCategoryDto);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<Object> updateCategory(@Valid @RequestBody CategoryDTO categoryDto, @PathVariable Integer categoryId ) {
		CategoryDTO updatedCategoryDto = this.categoryService.updateCategory(categoryDto, categoryId);
		return ResponseHandler.generateResponse("Category Updated Successfully", HttpStatus.OK, updatedCategoryDto);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<Object> deleteCategory(@PathVariable Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return ResponseHandler.generateResponse("Category Deleted Successfully", HttpStatus.OK, null);
	}
	
	@GetMapping("/")
	public ResponseEntity<Object> getAllCategories() {
		return ResponseHandler.generateResponse("Category Fetched Successfully", HttpStatus.OK, this.categoryService.getAllCategorys());
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<Object> getCategoryById(@PathVariable Integer categoryId) {
		return ResponseHandler.generateResponse("Category Fetched Successfully", HttpStatus.OK, this.categoryService.getCategoryById(categoryId));
	}
}
