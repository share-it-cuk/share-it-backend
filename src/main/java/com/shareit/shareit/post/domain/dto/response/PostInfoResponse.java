package com.shareit.shareit.post.domain.dto.response;

import java.util.List;

import com.shareit.shareit.post.domain.entity.Post;
import com.shareit.shareit.post.domain.entity.PostImage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostInfoResponse implements PostResponse {

	private Long id;
	private String title;
	private String content;
	private String hashTag;
	private Long cost;
	private Integer perDate;
	private Integer likeCount;
	private List<String> imageKeys;
	private boolean editor;
	private boolean liked;

	public static PostInfoResponse fromEntity(Post post) {
		return new PostInfoResponse(post.getId(),
			post.getTitle(),
			post.getContent(),
			post.getHashTag(),
			post.getCost(),
			post.getPerDate(),
			post.getLikes().size(),
			post.getPostImages().stream()
				.map(PostImage::getImageKey)
				.toList(),
			false,
			false
		);
	}

	public void updateEditorCheck() {
		this.editor = true;
	}

	public void updateLikedCheck() {
		this.liked = true;
	}

}
