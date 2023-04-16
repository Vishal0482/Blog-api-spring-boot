package com.vishal.blog.services;

import java.util.List;

import com.vishal.blog.payloads.UserDTO;

public interface UserService {
	UserDTO createUser(UserDTO userDTO);
	UserDTO updateUser(UserDTO userDTO, Integer userId);
	UserDTO getUserById(Integer userId);
	List<UserDTO> getAllUsers();
	void deleteUser(Integer userId);
	UserDTO registerNewUser(UserDTO user);
}
