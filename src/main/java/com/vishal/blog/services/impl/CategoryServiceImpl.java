package com.vishal.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vishal.blog.entities.Category;
import com.vishal.blog.exceptions.ResourceNotFoundException;
import com.vishal.blog.payloads.CategoryDTO;
import com.vishal.blog.repositories.CategoryRepo;
import com.vishal.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category category = this.modelMapper.map(categoryDTO, Category.class);
		Category createdCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(createdCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId.toString()));
		category.setCategoryName(categoryDTO.getCategoryName());
		category.setCategoryDescription(categoryDTO.getCategoryDescription());
		Category updatedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(updatedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO getCategoryById(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId.toString()));
		return this.modelMapper.map(category, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategorys() {
		List<Category> categoryList = this.categoryRepo.findAll();
		List<CategoryDTO> categoryDtoList = categoryList.stream().map(category -> this.modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
		return categoryDtoList;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId.toString()));
		this.categoryRepo.delete(category);
	}

}
