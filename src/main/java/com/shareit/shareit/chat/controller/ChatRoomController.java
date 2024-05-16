package com.shareit.shareit.chat.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shareit.shareit.chat.domain.response.ChatRoomListResponse;
import com.shareit.shareit.chat.domain.response.CreateChatRoomResponse;
import com.shareit.shareit.chat.service.ChatRoomService;
import com.shareit.shareit.domain.Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatRoomController {

	private final ChatRoomService chatRoomService;

	@GetMapping("/chatroom")
	Response<List<ChatRoomListResponse>> getAllRooms() {
		return chatRoomService.findAllChatRooms();
	}

	@PostMapping("/chatroom/{postId}")
	Response<CreateChatRoomResponse> createChatRoom(@PathVariable("postId") Long postId) {
		return chatRoomService.getChatRoom(postId);
	}
}
