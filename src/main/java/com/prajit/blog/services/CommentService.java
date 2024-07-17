package com.prajit.blog.services;

import com.prajit.blog.payloads.CommentDto;

public interface CommentService {
	CommentDto createComment(CommentDto commentdto,Integer postId);
	void deleteComment(Integer commentId);
	
}
