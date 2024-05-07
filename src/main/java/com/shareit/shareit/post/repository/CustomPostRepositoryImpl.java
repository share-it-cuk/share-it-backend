package com.shareit.shareit.post.repository;

import java.time.LocalDateTime;
import java.util.List;


import com.querydsl.core.types.dsl.BooleanExpression;
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
	public List<Post> findPostWithCursor(LocalDateTime cursor, String keyword, PostType postType, int size) {

		BooleanExpression cursorInitial = cursor == null ? null : qPost.updatedAt.lt(cursor);
		BooleanExpression allOrKeyword = keyword == null ? null : qPost.title.contains(keyword);
		BooleanExpression typedSearch = postType == null ? null : qPost.postType.eq(postType);

		return query
			.selectFrom(qPost)
			.leftJoin(qPost.postImages)
			.fetchJoin()
			.where(
				cursorInitial,
				allOrKeyword,
				typedSearch
			)
			.orderBy(qPost.updatedAt.desc())
			.limit(size)
			.fetch();
	}
}
