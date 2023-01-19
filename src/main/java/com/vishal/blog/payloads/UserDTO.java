package com.vishal.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	private int id;

	@NotEmpty
	@Size(min = 4, message = "User name length must be greater than 4.")
	private String name;
	@Email
	private String email;
	@NotEmpty
	@Size(min = 6, max = 16, message = "User name length must be between 6 to 16.")
	private String password;
	@NotEmpty
	private String about;
}
