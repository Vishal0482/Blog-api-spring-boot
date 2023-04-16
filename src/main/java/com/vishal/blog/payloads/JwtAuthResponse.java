package com.vishal.blog.payloads;

import lombok.Data;

@Data
public class JwtAuthResponse extends UserDTO {
	private String token;
//	private UserDTO user;
}
