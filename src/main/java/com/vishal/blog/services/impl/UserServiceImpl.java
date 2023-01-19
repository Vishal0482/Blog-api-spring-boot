package com.vishal.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vishal.blog.entities.User;
import com.vishal.blog.exceptions.ResourceNotFoundException;
import com.vishal.blog.payloads.UserDTO;
import com.vishal.blog.repositories.UserRepo;
import com.vishal.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = this.modelMapper.map(userDTO, User.class);
		User SavedUser = userRepo.save(user);
		return this.modelMapper.map(SavedUser, UserDTO.class);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		return this.modelMapper.map(updatedUser, UserDTO.class);
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		return this.modelMapper.map(user, UserDTO.class);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> userList = this.userRepo.findAll();
		List<UserDTO> userDtoList = userList.stream().map(user -> this.modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
		return userDtoList;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		this.userRepo.delete(user);
	}

}
