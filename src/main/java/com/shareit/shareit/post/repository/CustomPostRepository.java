package com.shareit.shareit.post.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.shareit.shareit.post.domain.PostType;
import com.shareit.shareit.post.domain.entity.Post;

public interface CustomPostRepository {

	List<Post> findAllPostInitial(String keyword, Pageable pageable);

	List<Post> findAllWithCursorPaging(LocalDateTime cursorTime, Long cursorId, String keyword, Pageable pageRequest);

	List<Post> findTypedPostInitial(PostType type, String keyword, Pageable pageRequest);

	List<Post> findTypePost(PostType postType, LocalDateTime cursorTime, Long cursorId, String keyword, Pageable pageRequest);
}
