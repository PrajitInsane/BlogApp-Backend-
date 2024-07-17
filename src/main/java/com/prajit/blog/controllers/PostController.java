package com.prajit.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prajit.blog.entities.Post;
import com.prajit.blog.payloads.ApiResponse;
import com.prajit.blog.payloads.PostDto;
import com.prajit.blog.payloads.PostResponse;
import com.prajit.blog.services.FileService;
import com.prajit.blog.services.PostServices;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;


@Tag(name="Posts",description = "User can post or delete posts")
@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostServices postservice;
	
	@Autowired
	private FileService fileservice;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId){
		PostDto createPost=this.postservice.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		List<PostDto> posts=this.postservice.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> posts=this.postservice.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue = "10" ,required=false)Integer pageNumber,
			@RequestParam(value = "pagesize",defaultValue="1",required = false)Integer pageSize
			){
		PostResponse response=this.postservice.getAllPost(pageNumber, pageSize);
		return new ResponseEntity<PostResponse>(response,HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postid}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postid){
		PostDto postDto=this.postservice.getPostById(postid);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ApiResponse delete(@PathVariable Integer postId) {
		this.postservice.deletePost(postId);
		return new ApiResponse("Post Successfully deleted", true);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postdto,@PathVariable Integer postId){
		PostDto updatePost=this.postservice.updatePost(postdto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords){
		List<PostDto>posts=this.postservice.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image")MultipartFile image,@PathVariable Integer postId) throws IOException{
		PostDto postDto= this.postservice.getPostById(postId);
		String fileName=this.fileservice.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatepost=this.postservice.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatepost,HttpStatus.OK);
		
	}
	
	@GetMapping(value="/posts/image/{ImageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("ImageName")String imageName,HttpServletResponse response) throws IOException {
		InputStream resource =this.fileservice.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
