package com.shareit.shareit.like.service;

import com.shareit.shareit.domain.Response;
import com.shareit.shareit.like.domain.dto.LikeRequest;

public interface LikeService {

	Response<Void> postLike(LikeRequest likeRequest);

}
