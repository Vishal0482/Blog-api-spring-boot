package com.vishal.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

	private int id;
	@NotEmpty
	private String categoryName;
	@NotEmpty
	@Size(min = 10, message = "Description length must be longer than 10.")
	private String categoryDescription;
}
