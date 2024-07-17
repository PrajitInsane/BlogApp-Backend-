package com.prajit.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prajit.blog.entities.Comment;
import com.prajit.blog.payloads.ApiResponse;
import com.prajit.blog.payloads.CommentDto;
import com.prajit.blog.services.CommentService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Comment",description = "User can post and delete comments")
@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentservice;
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto>createcomment(@RequestBody CommentDto commentdto,@PathVariable Integer postId){
		CommentDto createcomment=this.commentservice.createComment(commentdto, postId);
		return new ResponseEntity<CommentDto>(createcomment,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse>deleteComment(@PathVariable Integer commentId){
		this.commentservice.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully",true),HttpStatus.OK);
	}
}
