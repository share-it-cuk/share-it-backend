package com.shareit.shareit.chat.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shareit.shareit.chat.domain.entity.ChatRoom;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomListResponse {

	private Long chatRoomId;
	private String title;

	public static ChatRoomListResponse fromEntity(ChatRoom chatRoom) {
		ChatRoomListResponse response = new ChatRoomListResponse();

		response.chatRoomId = chatRoom.getId();
		response.title = chatRoom.getTitle();

		return response;
	}
}
