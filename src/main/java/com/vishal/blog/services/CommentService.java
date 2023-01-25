package com.vishal.blog.services;

import com.vishal.blog.payloads.CommentDTO;

public interface CommentService {

	public CommentDTO createComment(CommentDTO commentDto, Integer postId);
	void deleteComment(Integer commentId);
}
