package com.shareit.shareit.chat.domain.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateChatRoomResponse {

	private Long chatRoomId;

	public static CreateChatRoomResponse of(Long chatRoomId){
		return new CreateChatRoomResponse(chatRoomId);
	}

}
