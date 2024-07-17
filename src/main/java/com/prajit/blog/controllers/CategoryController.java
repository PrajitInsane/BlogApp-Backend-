package com.prajit.blog.controllers;

import java.util.List;

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

import com.prajit.blog.payloads.ApiResponse;
import com.prajit.blog.payloads.CategoryDto;
import com.prajit.blog.services.CategoryServices;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Catgeory",description="Posts are categorized for effecient search")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
		
	@Autowired
	private CategoryServices categoryservice;
	
	//POST
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categorydto){
		CategoryDto createCategory=this.categoryservice.createCategory(categorydto);
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	
	//PUT
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categorydto,@PathVariable Integer catId){
		CategoryDto updatedCategory=this.categoryservice.updateCategory(categorydto, catId);
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
	}
	
	//DELETE
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
		this.categoryservice.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted succcessfully",true),HttpStatus.OK);
	}
	
	//GET
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId){
		CategoryDto categorydto=categoryservice.getCategory(catId);
		return new ResponseEntity<CategoryDto>(categorydto,HttpStatus.OK);
	}
	
	//GET ALL CATEGORIES
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategories(){
		List<CategoryDto> categories=this.categoryservice.getCategories();
		return ResponseEntity.ok(categories);
	}
	
}
