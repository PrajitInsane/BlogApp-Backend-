package com.prajit.blog.services;

import com.prajit.blog.payloads.CategoryDto;

import java.util.List;

public interface CategoryServices {
	
	//Create
	CategoryDto createCategory(CategoryDto categorydto);
	
	//Update
	CategoryDto updateCategory(CategoryDto categorydto,Integer categoryid);
	
	//Delete
	void deleteCategory(Integer categoryid);
	
	//Read
	CategoryDto getCategory(Integer categoryid);
	
	//Read All
	List<CategoryDto> getCategories();
	
}
