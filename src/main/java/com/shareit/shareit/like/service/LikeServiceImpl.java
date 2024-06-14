package com.shareit.shareit.like.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shareit.shareit.Exceptions.BusinessException;
import com.shareit.shareit.domain.Response;
import com.shareit.shareit.domain.ResponseCode;
import com.shareit.shareit.like.domain.dto.LikeRequest;
import com.shareit.shareit.like.domain.entity.Likes;
import com.shareit.shareit.like.repository.LikeRepository;
import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.post.domain.entity.Post;
import com.shareit.shareit.post.repository.PostRepository;
import com.shareit.shareit.utils.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

	private final SecurityUtils securityUtils;
	private final PostRepository postRepository;
	private final LikeRepository likeRepository;

	@Override
	public Response<Void> postLike(LikeRequest likeRequest) {

		Member currentMember = securityUtils.getMember();

		Post currentPost = postRepository.findById(likeRequest.getPostId()).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.POST_NOT_FOUND));

		Optional<Likes> findLike = likeRepository.findLikesWithMemberPost(currentMember, currentPost);

		if (findLike.isPresent()) {
			likeRepository.delete(findLike.get());
		} else {
			Likes like = Likes.builder()
				.post(currentPost)
				.member(currentMember)
				.build();

			likeRepository.save(like);
		}

		return Response.ok();
	}
}
