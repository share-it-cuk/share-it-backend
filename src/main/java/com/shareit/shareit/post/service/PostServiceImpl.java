package com.shareit.shareit.post.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shareit.shareit.Exceptions.BusinessException;
import com.shareit.shareit.domain.Repository.MemberRepository;
import com.shareit.shareit.domain.Response;
import com.shareit.shareit.domain.ResponseCode;
import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.post.domain.PostType;
import com.shareit.shareit.post.domain.dto.request.CreatePostRequest;
import com.shareit.shareit.post.domain.dto.request.EditPostRequest;
import com.shareit.shareit.post.domain.dto.response.PostInfoForList;
import com.shareit.shareit.post.domain.dto.response.PostInfoResponse;
import com.shareit.shareit.post.domain.dto.response.PostInfoWithPaging;
import com.shareit.shareit.post.domain.entity.Post;
import com.shareit.shareit.post.repository.PostRepository;
import com.shareit.shareit.utils.SecurityUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final SecurityUtils securityUtils;
	private final MemberRepository memberRepository;

	/**
	 * create new post with current session member
	 *
	 * @param request : title, content, cost, postType, perDate, hashTag, campus, member
	 * @return : ok response
	 */
	@Override
	public Response<Void> createPost(CreatePostRequest request) {

		// securityUtils.getContextUserInfo()
		Member contextUserInfo = memberRepository.findById(1L)
			.stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

		Post needPost = Post.builder()
			.postType(request.getPostType())
			.title(request.getTitle())
			.content(request.getContent())
			.cost(request.getCost())
			.hashTag(request.getHashTag())
			.campus(contextUserInfo.getCampus())
			.member(contextUserInfo)
			.perDate(request.getPerDate())
			.build();

		try {
			postRepository.save(needPost);
		} catch (Exception e) {
			throw new BusinessException();
		}

		return Response.ok();
	}

	@Override
	public Response<PostInfoWithPaging> getPagedPosts(LocalDateTime cursor, String keyword, PostType postType,
		int size) {

		boolean hasNext = true;
		int lastCursorIndex = size - 1;

		List<Post> findPosts = postRepository.findPostWithCursor(cursor, keyword, postType, size + 1);

		if (findPosts.isEmpty()){
			return Response.ok(
				PostInfoWithPaging.builder()
					.hasNext(false)
					.cursor(cursor)
					.build()
			);
		}

		if (findPosts.size() < size + 1) {
			hasNext = false;
			lastCursorIndex = findPosts.size() - 1;
		}

		return Response.ok(
			PostInfoWithPaging.builder()
				.hasNext(hasNext)
				.cursor(findPosts.get(lastCursorIndex).getUpdatedAt())
				.postInfos(
					findPosts.stream()
						.limit(size)
						.map(PostInfoForList::fromEntity)
						.toList()
				)
				.build()
		);
	}

	/**
	 * @param postId : post id which going to find
	 * @return : post detail info response
	 */
	@Override
	public Response<PostInfoResponse> getPostDetail(Long postId) {

		// ContextUserInfo contextUserInfo = securityUtils.getContextUserInfo();
		Member contextUserInfo = memberRepository.findById(1L)
			.stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

		Member member = memberRepository.findById(contextUserInfo.getId())
			.stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

		Post post = postRepository.findPostById(postId)
			.stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.POST_NOT_FOUND));

		PostInfoResponse postResponse = PostInfoResponse.fromEntity(post);

		if (post.getMember() == member) {
			postResponse.updateEditorCheck();
		}

		return Response.ok(postResponse);
	}

	/**
	 * @param request : String title, String content, Long cost, String hashtag
	 * @return : ok message return
	 */
	@Override
	public Response<Void> editPost(EditPostRequest request) {

		Post post = findPostWithAuthenticationCheck(request.getId());

		post.updateTitle(request.getTitle());
		post.updateContent(request.getContent());
		post.updateCost(request.getCost());
		post.updateHashTag(request.getHashTag());

		return Response.ok();
	}

	/**
	 * @param postId : id which going to delete
	 * @return : ok message return
	 */
	@Override
	public Response<Void> deletePost(Long postId) {

		Post deletePost = findPostWithAuthenticationCheck(postId);

		postRepository.delete(deletePost);

		return Response.ok();
	}

	private Post findPostWithAuthenticationCheck(Long postId) {
		// Member member = securityUtils.getContextUserInfo().getMember();

		Member findMember = memberRepository.findById(1L)
			.stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

		Post post = postRepository.findPostById(postId)
			.stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.POST_NOT_FOUND));

		if (post.getMember() != findMember) {
			throw new BusinessException(ResponseCode.POST_UNAUTHORIZED);
		}

		return post;
	}
}
