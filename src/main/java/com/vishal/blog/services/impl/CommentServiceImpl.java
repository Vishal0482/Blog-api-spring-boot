package com.vishal.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vishal.blog.entities.Comment;
import com.vishal.blog.entities.Post;
import com.vishal.blog.exceptions.ResourceNotFoundException;
import com.vishal.blog.payloads.CommentDTO;
import com.vishal.blog.repositories.CommentRepo;
import com.vishal.blog.repositories.PostRepo;
import com.vishal.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper moedlMapper;

	@Override
	public CommentDTO createComment(CommentDTO commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		Comment comment = this.moedlMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment createdComment = this.commentRepo.save(comment);
		return this.moedlMapper.map(createdComment, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
		this.commentRepo.delete(comment);
	}

}
