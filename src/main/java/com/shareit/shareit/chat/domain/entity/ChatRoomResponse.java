package com.shareit.shareit.chat.domain.entity;

import java.util.List;

import com.shareit.shareit.chat.domain.response.ChatRoomListResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponse {

	private Long userId;
	private List<ChatRoomListResponse> list;
}
