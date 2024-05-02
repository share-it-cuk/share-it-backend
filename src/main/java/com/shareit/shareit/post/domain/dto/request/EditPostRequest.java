package com.shareit.shareit.post.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditPostRequest {

	@NotNull
	private Long id;
	private String title;
	private String content;
	private Long cost;
	private String hashTag;

}
