package com.shareit.shareit.post.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditPostRequest {

	private Long id;
	private String title;
	private String content;
	private Long cost;
	private String hashTag;

}
