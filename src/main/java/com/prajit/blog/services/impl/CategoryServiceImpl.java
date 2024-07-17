package com.prajit.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prajit.blog.entities.Category;
import com.prajit.blog.exceptions.ResourceNotFoundException;
import com.prajit.blog.payloads.CategoryDto;
import com.prajit.blog.repositories.CategoryRepo;
import com.prajit.blog.services.CategoryServices;

@Service
public class CategoryServiceImpl implements CategoryServices{
	
	@Autowired
	private CategoryRepo categoryrepo;
	
	@Autowired
	private ModelMapper modelmapper;

	@Override
	public CategoryDto createCategory(CategoryDto categorydto) {
		Category cat=this.modelmapper.map(categorydto, Category.class);
		Category created=this.categoryrepo.save(cat);
		return this.modelmapper.map(created, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categorydto, Integer categoryid) {
		Category cat=this.categoryrepo.findById(categoryid).orElseThrow(()-> new ResourceNotFoundException("Category","category_id",categoryid));
		cat.setCategoryTitle(categorydto.getCategoryTitle());
		cat.setCategoryTitle(categorydto.getCategoryDescription());
		Category updated=this.categoryrepo.save(cat);
		return this.modelmapper.map(updated,CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryid) {
		Category cat=this.categoryrepo.findById(categoryid).orElseThrow(()->new ResourceNotFoundException("Category","category_id", categoryid));
		this.categoryrepo.delete(cat);
		
	}

	@Override
	public CategoryDto getCategory(Integer categoryid) {
		Category cat=this.categoryrepo.findById(categoryid).orElseThrow(()->new ResourceNotFoundException("Category","category_id", categoryid));
		return this.modelmapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category>categories=this.categoryrepo.findAll();
		List<CategoryDto> catDtos=categories.stream().map((cat)->this.modelmapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return catDtos;
	}

}
