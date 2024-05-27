package com.shareit.shareit.chat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shareit.shareit.Exceptions.BusinessException;
import com.shareit.shareit.chat.domain.entity.ChatMember;
import com.shareit.shareit.chat.domain.entity.ChatRoom;
import com.shareit.shareit.chat.domain.response.ChatRoomListResponse;
import com.shareit.shareit.chat.domain.response.CreateChatRoomResponse;
import com.shareit.shareit.chat.repository.ChatMemberRepository;
import com.shareit.shareit.chat.repository.ChatRoomRepository;
import com.shareit.shareit.domain.Repository.MemberRepository;
import com.shareit.shareit.domain.Response;
import com.shareit.shareit.domain.ResponseCode;
import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.post.domain.entity.Post;
import com.shareit.shareit.post.repository.PostRepository;
import com.shareit.shareit.utils.SecurityUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

	private final ChatRoomRepository chatRoomRepository;
	private final ChatMemberRepository chatMemberRepository;
	private final PostRepository postRepository;
	//TODO: 삭제
	private final MemberRepository memberRepository;
	private final SecurityUtils securityUtils;

	@Override
	public Response<List<ChatRoomListResponse>> findAllChatRooms() {

		// Member sessionMember = securityUtils.getContextUserInfo().getMember();

		Member sessionMember = memberRepository.findById(2L).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

		List<ChatRoomListResponse> list = chatMemberRepository.findAllChatMemberWithChatRoom(sessionMember).stream()
			.map(chatMember -> ChatRoomListResponse.fromEntity(chatMember.getChatRoom()))
			.toList();

		return Response.ok(list);
	}

	@Override
	public Response<CreateChatRoomResponse> getChatRoom(Long postId) {

		// Member sessionMember = securityUtils.getContextUserInfo().getMember();

		Member sessionMember = memberRepository.findById(2L).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

		Post post = postRepository.findPostById(postId).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.POST_NOT_FOUND));

		List<ChatRoom> chatRooms = chatRoomRepository.findChatRoomsByPost(post);

		Optional<ChatRoom> existChatRoom = chatRooms.stream()
			.filter(chatRoom ->
				chatRoom.getChatMembers().stream()
					.anyMatch(chatMember -> chatMember.getMember().equals(sessionMember))
			)
			.findAny();

		if (existChatRoom.isEmpty()){
			ChatRoom createdChatRoom = ChatRoom.builder()
				.title(post.getTitle())
				.post(post)
				.build();

			chatRoomRepository.save(createdChatRoom);
			ChatMember sessionChatMember = ChatMember.builder()
				.chatRoom(createdChatRoom)
				.member(sessionMember)
				.build();

			ChatMember postMember = ChatMember.builder()
				.chatRoom(createdChatRoom)
				.member(post.getMember())
				.build();

			chatMemberRepository.save(sessionChatMember);
			chatMemberRepository.save(postMember);

			return Response.ok(CreateChatRoomResponse.of(createdChatRoom.getId()));
		}

		existChatRoom.get().updateTitle(post.getTitle());

		return Response.ok(CreateChatRoomResponse.of(existChatRoom.get().getId()));
	}
}
