package com.shareit.shareit.chat.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

	private Long roomId;
	private String sender;
	private String message;
}
