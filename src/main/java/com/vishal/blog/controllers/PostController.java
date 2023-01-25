package com.vishal.blog.controllers;

import java.io.FileNotFoundException;
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

import com.vishal.blog.config.Constants;
import com.vishal.blog.payloads.PaginationDataHandler;
import com.vishal.blog.payloads.PostDTO;
import com.vishal.blog.payloads.ResponseHandler;
import com.vishal.blog.services.FileService;
import com.vishal.blog.services.PostService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<Object> createPost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDTO createdPost = this.postService.createPost(postDTO, userId, categoryId);
		return ResponseHandler.generateResponse("Post Created Sucessfully.", HttpStatus.OK, createdPost);
	}

	@GetMapping("/post")
	public ResponseEntity<Object> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_POST_SORY_BY, required = false) String soryBy,
			@RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		PaginationDataHandler<PostDTO> postDtoList = this.postService.getAllPost(pageNumber, pageSize, soryBy, sortDir);
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

	@PutMapping("/post/{postId}")
	public ResponseEntity<Object> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer postId) {
		PostDTO postDto = this.postService.updatePost(postDTO, postId);
		return ResponseHandler.generateResponse("Posts Updated Sucessfully.", HttpStatus.OK, postDto);
	}

	@DeleteMapping("/post/{postId}")
	public ResponseEntity<Object> deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return ResponseHandler.generateResponse("Posts Deleted Sucessfully.", HttpStatus.OK, null);
	}

	@GetMapping("/post/title/{title}")
	public ResponseEntity<Object> deletePost(@PathVariable String title) {
		List<PostDTO> postDtoList = this.postService.searchPost(title);
		return ResponseHandler.generateResponse("Posts Fetched Sucessfully.", HttpStatus.OK, postDtoList);
	}
	
	@PostMapping("/post/{postId}/image")
	public ResponseEntity<Object> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException {
		PostDTO postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageUrl(fileName);
		PostDTO updatedPost = this.postService.updatePost(postDto, postId);
		return ResponseHandler.generateResponse("Image Uploaded Successfully.", HttpStatus.OK, updatedPost);
	
	}
	
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
