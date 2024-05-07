package com.shareit.shareit.post.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.shareit.shareit.post.domain.PostType;
import com.shareit.shareit.post.domain.entity.Post;

public interface CustomPostRepository {

	List<Post> findPostWithCursor(LocalDateTime cursor, String keyword, PostType postType, int size);
}
