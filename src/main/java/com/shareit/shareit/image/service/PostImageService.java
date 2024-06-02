package com.shareit.shareit.image.service;

import com.shareit.shareit.domain.Response;
import com.shareit.shareit.image.dto.ImageUploadRequest;

public interface PostImageService {

	Response<Void> uploadPostImage(ImageUploadRequest request);

}
