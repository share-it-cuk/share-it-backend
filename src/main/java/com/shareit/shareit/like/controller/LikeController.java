package com.shareit.shareit.like.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shareit.shareit.domain.Response;
import com.shareit.shareit.like.domain.dto.LikeRequest;
import com.shareit.shareit.like.service.LikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;

	@PostMapping("/like")
	public Response<Void> postLike(@RequestBody LikeRequest request) {

		return likeService.postLike(request);
	}
}
