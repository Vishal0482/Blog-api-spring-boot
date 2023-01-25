package com.vishal.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.blog.payloads.CommentDTO;
import com.vishal.blog.payloads.ResponseHandler;
import com.vishal.blog.services.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comment")
	public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDTO commentDto, @PathVariable Integer postId) {
		CommentDTO createdComment = this.commentService.createComment(commentDto, postId);
		return ResponseHandler.generateResponse("Comment Created Successfully.", HttpStatus.OK, createdComment);
	}
	
	@DeleteMapping("/post/{postId}/comment/{commentId}")
	public ResponseEntity<Object> deleteComment(@PathVariable Integer commentId) {
		this.commentService.deleteComment(commentId);
		return ResponseHandler.generateResponse("Comment Created Successfully.", HttpStatus.OK, null);
	}
}
