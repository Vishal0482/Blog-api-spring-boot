package com.vishal.blog.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.blog.entities.User;
import com.vishal.blog.exceptions.ApiException;
import com.vishal.blog.payloads.JwtAuthRequest;
import com.vishal.blog.payloads.JwtAuthResponse;
import com.vishal.blog.payloads.ResponseHandler;
import com.vishal.blog.payloads.UserDTO;
import com.vishal.blog.security.JwtTokenHelper;
import com.vishal.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<Object> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setEmail(userDetails.getUsername());

//		response.setUser(this.mapper.map(userDetails, UserDTO.class));
		return ResponseHandler.generateResponse("Login Successfully.", HttpStatus.OK, response);
	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken
		authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			Authentication authenticate = this.authenticationManager.authenticate(authenticationToken);
			System.out.println(authenticate);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Detials !!");
			throw new ApiException("Invalid username or password !!");
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<Object> registerUser(@Valid @RequestBody UserDTO userDto) {
		UserDTO registeredUser = this.userService.registerNewUser(userDto);
		return ResponseHandler.generateResponse("Register Successfully.", HttpStatus.CREATED, registeredUser);
	}
}
