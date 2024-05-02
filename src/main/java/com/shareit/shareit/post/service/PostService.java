package com.shareit.shareit.post.service;

import java.time.LocalDateTime;

import com.shareit.shareit.domain.Response;
import com.shareit.shareit.post.domain.PostType;
import com.shareit.shareit.post.domain.dto.request.CreatePostRequest;
import com.shareit.shareit.post.domain.dto.request.EditPostRequest;
import com.shareit.shareit.post.domain.dto.response.PostInfoResponse;
import com.shareit.shareit.post.domain.dto.response.PostInfoWithPaging;

public interface PostService {

	Response<Void> createPost(CreatePostRequest request);

	Response<PostInfoWithPaging> getSearchPosts(LocalDateTime cursorTime, Long cursorId, Integer limit, String keyword);

	Response<PostInfoWithPaging> getSearchPostsWithFilter(LocalDateTime cursorTime, Long cursorId, Integer limit, String keyword, PostType postType);

	Response<PostInfoResponse> getPostDetail(Long postId);

	Response<Void> editPost(EditPostRequest request);

	Response<Void> deletePost(Long postId);

}
