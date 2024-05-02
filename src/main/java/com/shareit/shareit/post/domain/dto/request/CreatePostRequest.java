package com.shareit.shareit.post.domain.dto.request;

import com.shareit.shareit.post.domain.PostType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreatePostRequest {

	@NotNull
	private String title;
	@NotNull
	private String content;
	@NotNull
	private Long cost;
	private String hashTag;
	private Integer perDate;
	@NotNull
	private PostType postType;

}
