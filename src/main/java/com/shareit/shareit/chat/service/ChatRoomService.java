package com.shareit.shareit.chat.service;

import java.util.List;

import com.shareit.shareit.chat.domain.entity.ChatRoomResponse;
import com.shareit.shareit.chat.domain.response.CreateChatRoomResponse;
import com.shareit.shareit.domain.Response;

public interface ChatRoomService {

	Response<ChatRoomResponse> findAllChatRooms();

	Response<CreateChatRoomResponse> getChatRoom(Long postId);

}
