package com.prajit.blog.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prajit.blog.payloads.ApiResponse;
import com.prajit.blog.payloads.UserDto;
import com.prajit.blog.security.JWTAuthenticationFilter;
import com.prajit.blog.security.JwtHelper;
import com.prajit.blog.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name="User",description = "User details")
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
	
	@Autowired
	private UserService userservice;
	
	
	//POST -create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser( @RequestBody UserDto userdto){
		UserDto createUserDto=this.userservice.createUser(userdto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	//PUT - update user
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> UpdateUser(@Valid @RequestBody UserDto userdto,@PathVariable Integer userId){
		UserDto updateduser=this.userservice.updateUser(userdto, userId);
		return ResponseEntity.ok(updateduser);
	}
	
	//DELETE -delete user
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse>deleteUser(@PathVariable Integer userId){
		this.userservice. deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted",true),HttpStatus.OK);
	}
	
	//GET - get list of users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userservice.getAllUsers());
	}
	
	//GET - get single user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		return ResponseEntity.ok(this.userservice.getUserById(userId));
	}
	
	//GET -get current user
	@GetMapping("/current-user")
	public String getLoggedUser(Principal principal) {
		return principal.getName();
	}
}
