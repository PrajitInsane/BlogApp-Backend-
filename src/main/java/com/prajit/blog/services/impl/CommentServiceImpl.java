package com.prajit.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prajit.blog.entities.Comment;
import com.prajit.blog.entities.Post;
import com.prajit.blog.exceptions.ResourceNotFoundException;
import com.prajit.blog.payloads.CommentDto;
import com.prajit.blog.repositories.CommentRepo;
import com.prajit.blog.repositories.PostRepo;
import com.prajit.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	
	@Override
	public CommentDto createComment(CommentDto commentdto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
		Comment comment =this.modelmapper.map(commentdto, Comment.class);
		comment.setPost(post);
		Comment savedComment=this.commentRepo.save(comment);
		return this.modelmapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","comment id", commentId));
		this.commentRepo.delete(comment);
		
	}

}
