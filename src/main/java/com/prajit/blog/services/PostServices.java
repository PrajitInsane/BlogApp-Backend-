package com.prajit.blog.services;

import java.util.List;
import com.prajit.blog.entities.Post;
import com.prajit.blog.payloads.PostDto;
import com.prajit.blog.payloads.PostResponse;

public interface PostServices {
	
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) ;
	
	PostDto updatePost(PostDto posDto,Integer postId);
	
	void deletePost(Integer postId);
	
	PostResponse getAllPost(Integer pagenumber,Integer pagesize);
	
	PostDto getPostById(Integer postId);
	
	List<PostDto> getPostByCategory(Integer categoryId);
	
	List<PostDto> getPostsByUser(Integer userId);
	
	List<PostDto> searchPosts(String keyword);
	
	
}
