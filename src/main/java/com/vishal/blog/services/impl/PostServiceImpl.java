package com.vishal.blog.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.vishal.blog.entities.Category;
import com.vishal.blog.entities.Post;
import com.vishal.blog.entities.User;
import com.vishal.blog.exceptions.ResourceNotFoundException;
import com.vishal.blog.payloads.PaginationDataHandler;
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
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId.toString()));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId.toString()));

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
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId.toString()));
		post.setContent(postDTO.getContent());
		post.setTitle(postDTO.getTitle());
		post.setImageUrl(postDTO.getImageUrl());
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDTO.class);
	}

	@Override
	public PostDTO getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId.toString()));
		return this.modelMapper.map(post, PostDTO.class);
	}

	@Override
	public PaginationDataHandler<PostDTO> getAllPost(Integer pageNumber, Integer pageSize, String soryBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(soryBy).descending() : Sort.by(soryBy).ascending();
		Pageable pageData = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pageList = this.postRepo.findAll(pageData);
		
		List<Post> postList = pageList.getContent();
		List<PostDTO> postDtoList = postList.stream().map(post -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		
		PaginationDataHandler<PostDTO> paginationPostData = new PaginationDataHandler<PostDTO>();
		paginationPostData.setDocs(postDtoList);
		paginationPostData.setPageNumber(pageList.getNumber());
		paginationPostData.setPageSize(pageList.getSize());
		paginationPostData.setTotalPages(pageList.getTotalPages());
		paginationPostData.setTotalElements(pageList.getTotalElements());
		paginationPostData.setHasNextPage(pageList.hasNext());
		paginationPostData.setHasPrevPage(pageList.hasPrevious());
		
		return paginationPostData;
	}

	@Override
	public List<PostDTO> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId.toString()));
		List<Post> postList = this.postRepo.findByUser(user);
		List<PostDTO> postDtoList = postList.stream().map(post -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostDTO> getPostByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId.toString()));
		List<Post> postList = this.postRepo.findByCategory(category);
		List<PostDTO> postDtoList = postList.stream().map(post -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostDTO> searchPost(String title) {
		List<Post> postList = this.postRepo.findByTitleContaining(title);
		List<PostDTO> postDtoList = postList.stream().map(post -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId.toString()));
		this.postRepo.delete(post);
	}
}
