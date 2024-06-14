package com.shareit.shareit.post.domain.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.shareit.shareit.post.domain.entity.Post;
import com.shareit.shareit.post.domain.entity.PostImage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostInfoForList {

	private Long id;
	private String title;
	private String hashTag;
	private Long cost;
	private Integer perDate;
	private LocalDateTime updatedAt;
	private Integer likeCount;
	private List<String> postImageUrls;

	public static PostInfoForList fromEntity(Post post) {
		return new PostInfoForList(post.getId(),
			post.getTitle(),
			post.getHashTag(),
			post.getCost(),
			post.getPerDate(),
			post.getUpdatedAt(),
			post.getLikes().size(),
			post.getPostImages().stream()
				.map(PostImage::getImageKey)
				.toList()
		);
	}
}
