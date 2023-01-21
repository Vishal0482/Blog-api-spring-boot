package com.vishal.blog.payloads;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

	private int id;
	@NotEmpty
	private String title;
	@NotEmpty
	private String content;
	private String imageUrl;
	private Date createdAt;
	private CategoryDTO category;
	private UserDTO user;
}
