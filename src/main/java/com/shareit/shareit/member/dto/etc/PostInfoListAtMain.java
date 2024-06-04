package com.shareit.shareit.member.dto.etc;

import java.time.LocalDateTime;

import com.shareit.shareit.post.domain.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInfoListAtMain {

	private Long id;
	private String title;
	private Long cost;
	private LocalDateTime updatedAt;

	public static PostInfoListAtMain fromEntity(Post post) {
		return new PostInfoListAtMain(post.getId(),
			post.getTitle(),
			post.getCost(),
			post.getUpdatedAt()
		);
	}
}
