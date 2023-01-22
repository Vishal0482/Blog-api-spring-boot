package com.vishal.blog.services;

import java.util.List;

import com.vishal.blog.payloads.PaginationDataHandler;
import com.vishal.blog.payloads.PostDTO;

public interface PostService {

	PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

	PostDTO updatePost(PostDTO postDTO, Integer postId);

	PostDTO getPostById(Integer postId);

	PaginationDataHandler<PostDTO> getAllPost(Integer pageNumber, Integer pageSize, String soryBy, String sortDir);
	
	List<PostDTO> getPostByUser(Integer userId);
	
	List<PostDTO> getPostByCategory(Integer categoryId);
	
	List<PostDTO> searchPost(String keyword);

	void deletePost(Integer postId);
}
