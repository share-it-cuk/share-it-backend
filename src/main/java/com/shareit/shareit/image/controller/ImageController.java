package com.shareit.shareit.image.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shareit.shareit.domain.Response;
import com.shareit.shareit.image.dto.ImageUploadRequest;
import com.shareit.shareit.image.service.PostImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageController {

	private final PostImageService postImageService;

	@PostMapping(value = "/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Response<Void> uploadPostImage(ImageUploadRequest request) {

		return postImageService.uploadPostImage(request);
	}
}
