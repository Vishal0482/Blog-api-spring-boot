package com.vishal.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaginationDataHandler<T> {
	private List<T> docs;
	private Integer totalPages;
	private Long totalElements;
	private Integer pageSize;
	private Integer pageNumber;
	private Boolean hasPrevPage;
	private Boolean hasNextPage;
}
