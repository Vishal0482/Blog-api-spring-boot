package com.vishal.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.blog.payloads.PostDTO;
import com.vishal.blog.payloads.ResponseHandler;
import com.vishal.blog.services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;

	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<Object> createPost(@Valid @RequestBody PostDTO postDTO,@PathVariable Integer userId, @PathVariable Integer categoryId) {
		PostDTO createdPost = this.postService.createPost(postDTO, userId, categoryId);
		return ResponseHandler.generateResponse("Post Created Sucessfully.", HttpStatus.OK, createdPost);
	}
	
	@GetMapping("/post")
	public ResponseEntity<Object> getAllPost() {
		List<PostDTO> postDtoList = this.postService.getAllPost();
		return ResponseHandler.generateResponse("Posts Fetched Sucessfully.", HttpStatus.OK, postDtoList);
	}
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<Object> getPostById(@PathVariable Integer postId) {
		PostDTO post = this.postService.getPostById(postId);
		return ResponseHandler.generateResponse("Post Fetched Sucessfully.", HttpStatus.OK, post);
	}
	
	
	@GetMapping("/user/{userId}/post")
	public ResponseEntity<Object> getPostByUser(@PathVariable Integer userId) {
		List<PostDTO> postDtoList = this.postService.getPostByUser(userId);
		return ResponseHandler.generateResponse("Posts Fetched Sucessfully.", HttpStatus.OK, postDtoList);
	}
	
	@GetMapping("/category/{categoryId}/post")
	public ResponseEntity<Object> getPostByCategory(@PathVariable Integer categoryId) {
		List<PostDTO> postDtoList = this.postService.getPostByCategory(categoryId);
		return ResponseHandler.generateResponse("Posts Fetched Sucessfully.", HttpStatus.OK, postDtoList);
	}
}
