package com.vishal.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vishal.blog.entities.Category;
import com.vishal.blog.entities.Post;
import com.vishal.blog.entities.User;
import com.vishal.blog.exceptions.ResourceNotFoundException;
import com.vishal.blog.payloads.PostDTO;
import com.vishal.blog.repositories.CategoryRepo;
import com.vishal.blog.repositories.PostRepo;
import com.vishal.blog.repositories.UserRepo;
import com.vishal.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		Post post = this.modelMapper.map(postDTO, Post.class);
		post.setImageUrl("default.png");
		post.setCreatedAt(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post createdPost = this.postRepo.save(post);
		return this.modelMapper.map(createdPost, PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostDTO getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		return this.modelMapper.map(post, PostDTO.class);
	}

	@Override
	public List<PostDTO> getAllPost() {
		List<Post> postList = this.postRepo.findAll();
		List<PostDTO> postDtoList = postList.stream().map(post -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostDTO> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		List<Post> postList = this.postRepo.findByUser(user);
		List<PostDTO> postDtoList = postList.stream().map(post -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostDTO> getPostByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Post> postList = this.postRepo.findByCategory(category);
		List<PostDTO> postDtoList = postList.stream().map(post -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostDTO> searchPost(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub

	}

}
