package com.shareit.shareit.post.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shareit.shareit.domain.Response;
import com.shareit.shareit.post.domain.PostType;
import com.shareit.shareit.post.domain.dto.request.CreatePostRequest;
import com.shareit.shareit.post.domain.dto.request.EditPostRequest;
import com.shareit.shareit.post.domain.dto.response.PostInfoResponse;
import com.shareit.shareit.post.domain.dto.response.PostInfoWithPaging;
import com.shareit.shareit.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping("/post")
	public Response<Void> create(@RequestBody CreatePostRequest request) {
		return postService.createPost(request);
	}

	@GetMapping("/posts")
	public Response<PostInfoWithPaging> searchFromAllPost(
		@RequestParam(value = "cursor", required = false) LocalDateTime cursor,
		@RequestParam(value = "keyword", required = false) String keyword,
		@RequestParam(value = "postType", required = false) PostType postType,
		@RequestParam("limit") Integer limit
		) {

		return postService.getPagedPosts(cursor, keyword, postType, limit);
	}

	@GetMapping("/post/{postId}")
	public Response<PostInfoResponse> getPostDetail(@PathVariable("postId") Long postId) {
		return postService.getPostDetail(postId);
	}

	@PatchMapping("/post")
	public Response<Void> updatePost(@RequestBody EditPostRequest request) {

		return postService.editPost(request);
	}

	@DeleteMapping("/post/{postId}")
	public Response<Void> deletePost(@PathVariable("postId") Long postId) {

		return postService.deletePost(postId);
	}

}
