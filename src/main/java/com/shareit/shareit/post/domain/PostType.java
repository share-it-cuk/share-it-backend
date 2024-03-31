package com.shareit.shareit.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostType {
	NEED("NEED"), LENT("LENT");

	private final String postType;

}
