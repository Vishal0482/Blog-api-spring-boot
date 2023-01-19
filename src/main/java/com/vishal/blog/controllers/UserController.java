package com.vishal.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.blog.payloads.ResponseHandler;
import com.vishal.blog.payloads.UserDTO;
import com.vishal.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<Object> createUser(@Valid @RequestBody UserDTO userDto) {
		UserDTO createdUserDto = this.userService.createUser(userDto);
		return ResponseHandler.generateResponse("User Created Successfully", HttpStatus.OK, createdUserDto);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDto,@PathVariable("userId") Integer uId ) {
		UserDTO updatedUserDto = this.userService.updateUser(userDto, uId);
		return ResponseHandler.generateResponse("User Updated Successfully", HttpStatus.OK, updatedUserDto);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable Integer userId) {
		this.userService.deleteUser(userId);
		return ResponseHandler.generateResponse("User Deleted Successfully", HttpStatus.OK, null);
	}
	
	@GetMapping("/")
	public ResponseEntity<Object> getAllUsers() {
		return ResponseHandler.generateResponse("User Fetched Successfully", HttpStatus.OK, this.userService.getAllUsers());
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<Object> getUserById(@PathVariable Integer userId) {
		return ResponseHandler.generateResponse("Users Fetched Successfully", HttpStatus.OK, this.userService.getUserById(userId));
	}
	
}
