package com.prajit.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prajit.blog.entities.User;
import java.util.List;


public interface UserRepo  extends JpaRepository<User,Integer>{
	
}
