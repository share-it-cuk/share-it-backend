package com.shareit.shareit.post.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
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

	/**
	 * @param cursorTime : cursor time that front sent;
	 * @param cursorId   : cursor id that front sent
	 * @param limit      : size of page limit;
	 * @param keyword    : keyword that has to be searched. If null, find all of it;
	 * @return : gave response with post infos
	 */
	@Override
	public Response<PostInfoWithPaging> getSearchPosts(LocalDateTime cursorTime, Long cursorId, Integer limit,
		String keyword) {

		PageRequest pageRequest = PageRequest.ofSize(limit);
		boolean hasNext = true;
		List<PostInfoForList> posts;

		if (cursorTime == null || cursorId == null) {

			posts = postRepository.findAllPostInitial(keyword, pageRequest)
				.stream()
				.map(PostInfoForList::fromEntity)
				.toList();

		} else {

			posts = postRepository.findAllWithCursorPaging(cursorTime, cursorId, keyword, pageRequest)
				.stream()
				.map(PostInfoForList::fromEntity)
				.toList();

		}

		if (posts.size() != limit) {
			hasNext = false;
		}

		PostInfoWithPaging response;

		if (!posts.isEmpty()) {
			response = PostInfoWithPaging.builder()
				.hasNext(hasNext)
				.cursorTime(posts.get(posts.size() - 1).getUpdatedAt())
				.cursorId(posts.get(posts.size() - 1).getId())
				.postInfos(posts)
				.build();
		} else {
			response = PostInfoWithPaging.builder()
				.hasNext(hasNext)
				.postInfos(posts)
				.build();
		}

		return Response.ok(response);
	}

	/**
	 * @param cursorTime : cursor time front sent
	 * @param cursorId   : cursor id front sent
	 * @param limit      : size of one page
	 * @param keyword    : keyword which going to find
	 * @param postType   : post type which going to find
	 * @return : post infos and cursor infos
	 */
	@Override
	public Response<PostInfoWithPaging> getSearchPostsWithFilter(LocalDateTime cursorTime, Long cursorId, Integer limit,
		String keyword, PostType postType) {

		boolean hasNext = true;

		PageRequest pageRequest = PageRequest.ofSize(limit);

		List<PostInfoForList> posts;

		if (cursorTime == null || cursorId == null) {
			posts = postRepository.findTypedPostInitial(postType, keyword, pageRequest)
				.stream()
				.map(PostInfoForList::fromEntity)
				.toList();

		} else {
			posts = postRepository.findTypePost(postType, cursorTime, cursorId, keyword, pageRequest)
				.stream()
				.map(PostInfoForList::fromEntity)
				.toList();
		}

		if (posts.size() != limit) {
			hasNext = false;
		}

		PostInfoWithPaging response;

		if (!posts.isEmpty()) {
			response = PostInfoWithPaging.builder()
				.hasNext(hasNext)
				.cursorTime(posts.get(posts.size() - 1).getUpdatedAt())
				.cursorId(posts.get(posts.size() - 1).getId())
				.postInfos(posts)
				.build();
		} else {
			response = PostInfoWithPaging.builder()
				.hasNext(hasNext)
				.postInfos(posts)
				.build();
		}

		return Response.ok(response);
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
