package com.shareit.shareit.post.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shareit.shareit.post.domain.PostType;
import com.shareit.shareit.post.domain.entity.Post;
import com.shareit.shareit.post.domain.entity.QPost;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomPostRepositoryImpl implements CustomPostRepository {

	private final JPAQueryFactory query;
	private final QPost qPost = QPost.post;

	@Override
	public List<Post> findAllPostInitial(String keyword, Pageable pageable) {

		return query.
			selectFrom(qPost)
			.leftJoin(qPost.postImages)
			.fetchJoin()
			.where(qPost.title.contains(keyword))
			.orderBy(qPost.updatedAt.desc(), qPost.id.desc())
			.limit(pageable.getPageSize())
			.fetch();
	}

	@Override
	public List<Post> findAllWithCursorPaging(LocalDateTime cursorTime, Long cursorId, String keyword,
		Pageable pageRequest) {
		return query
			.selectFrom(qPost)
			.leftJoin(qPost.postImages)
			.fetchJoin()
			.where(qPost.title.contains(keyword)
				.and(qPost.updatedAt.lt(cursorTime)
					.and(qPost.id.lt(cursorId))
				)
			)
			.orderBy(qPost.updatedAt.desc(), qPost.id.desc())
			.limit(pageRequest.getPageSize())
			.fetch();
	}

	@Override
	public List<Post> findTypedPostInitial(PostType type, String keyword, Pageable pageRequest) {
		return query
			.selectFrom(qPost)
			.leftJoin(qPost.postImages)
			.fetchJoin()
			.where(qPost.postType.eq(type)
				.and(qPost.title.contains(keyword))
			)
			.orderBy(qPost.updatedAt.desc(), qPost.id.desc())
			.limit(pageRequest.getPageSize())
			.fetch();
	}

	@Override
	public List<Post> findTypePost(PostType postType, LocalDateTime cursorTime, Long cursorId, String keyword,
		Pageable pageRequest) {
		return query
			.selectFrom(qPost)
			.leftJoin(qPost.postImages)
			.fetchJoin()
			.where(qPost.postType.eq(postType)
				.and(qPost.title.contains(keyword)
					.and(qPost.updatedAt.lt(cursorTime)
						.and(qPost.id.lt(cursorId))
					)
				)
			)
			.orderBy(qPost.updatedAt.desc(), qPost.id.desc())
			.limit(pageRequest.getPageSize())
			.fetch();
	}
}
