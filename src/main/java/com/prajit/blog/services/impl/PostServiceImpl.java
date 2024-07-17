package com.prajit.blog.services.impl;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.prajit.blog.entities.Category;
import com.prajit.blog.entities.Post;
import com.prajit.blog.entities.User;
import com.prajit.blog.exceptions.ResourceNotFoundException;
import com.prajit.blog.payloads.PostDto;
import com.prajit.blog.payloads.PostResponse;
import com.prajit.blog.repositories.CategoryRepo;
import com.prajit.blog.repositories.PostRepo;
import com.prajit.blog.repositories.UserRepo;
import com.prajit.blog.services.PostServices;

@Service
public class PostServiceImpl implements PostServices {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo; 
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id", userId));
		Category category =this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category", categoryId));
		Post post=this.modelmapper.map(postDto,Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newpost= this.postRepo.save(post);
		return this.modelmapper.map(newpost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedpost=this.postRepo.save(post);
		return this.modelmapper.map(updatedpost,PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pagenumber,Integer pagesize) {
		Pageable p=PageRequest.of(pagenumber,pagesize); 
		Page<Post> pagepost=this.postRepo.findAll(p);
		List<Post> allposts=pagepost.getContent();
		List<PostDto> postDtos=allposts.stream().map((post)->this.modelmapper.map(post,PostDto.class)).collect(Collectors.toList());
		PostResponse response=new PostResponse();
		response.setContent(postDtos);
		response.setPageNumber(pagepost.getNumber());
		response.setPageSize(pagepost.getSize());
		response.setTotalElements(pagepost.getTotalElements());
		response.setLastPages(pagepost.isLast());
		response.setTotalPages(pagepost.getTotalPages());
		return response;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow( ()->new ResourceNotFoundException("Post","post id",postId));
		return this.modelmapper.map(post,PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Catgeory","category_id",categoryId));
		List<Post> posts=this.postRepo.findByCategory(cat);
		List<PostDto> postdto=posts.stream().map((post)->this.modelmapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return postdto;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		List<Post> posts=this.postRepo.findByUser(user);
		List<PostDto> postdto=posts.stream().map((post)->this.modelmapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return postdto;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post>posts=this.postRepo.findByTitle("%"+keyword+"%");
		List<PostDto>collect=posts.stream().map((post)->this.modelmapper.map(post,PostDto.class)).collect(Collectors.toList());
		return collect;
	}
	
	
}
