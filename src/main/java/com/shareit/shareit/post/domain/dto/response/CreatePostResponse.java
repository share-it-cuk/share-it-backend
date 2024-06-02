package com.shareit.shareit.post.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostResponse {

	private Long postId;

	public static CreatePostResponse of(Long id){
		return new CreatePostResponse(id);
	}

}
