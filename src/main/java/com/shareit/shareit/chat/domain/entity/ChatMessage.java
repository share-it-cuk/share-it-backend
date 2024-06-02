package com.shareit.shareit.chat.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.shareit.shareit.chat.domain.DiscriminateType;

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
@Document(collection = "message")
public class ChatMessage {

	private Long roomId;
	private Long senderId;
	private String message;
	private LocalDateTime sendTime = LocalDateTime.now();
	@Builder.Default
	DiscriminateType discriminateType = DiscriminateType.MESSAGE;

}
