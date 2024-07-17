package com.prajit.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prajit.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer>{
	
}
