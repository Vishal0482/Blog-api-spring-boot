package com.vishal.blog.services;

import java.util.List;

import com.vishal.blog.payloads.CategoryDTO;

public interface CategoryService {
	CategoryDTO createCategory(CategoryDTO categoryDTO);
	CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);
	CategoryDTO getCategoryById(Integer categoryId);
	List<CategoryDTO> getAllCategorys();
	void deleteCategory(Integer categoryId);
}
