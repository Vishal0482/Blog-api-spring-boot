package com.vishal.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

	@NotEmpty
	private int categoryId;
	@NotEmpty
	private String categoryName;
	@NotEmpty
	private String categoryDescripton;
}
