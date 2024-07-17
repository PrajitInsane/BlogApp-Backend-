package com.prajit.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prajit.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{
	
}
