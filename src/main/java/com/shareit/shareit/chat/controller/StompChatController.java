package com.shareit.shareit.chat.controller;

import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shareit.shareit.chat.domain.entity.ChatMessage;
import com.shareit.shareit.chat.domain.entity.PurchaseMessage;
import com.shareit.shareit.chat.domain.response.ChatListResponse;
import com.shareit.shareit.chat.service.MessageService;
import com.shareit.shareit.domain.Response;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StompChatController {

	private final MessageService messageService;

	@MessageMapping(value = "/chat/message")
	public void message(ChatMessage chatMessage){

		messageService.sendAndSaveMessage(chatMessage);
	}

	@MessageMapping(value = "/chat/purchase")
	public void purchaseMessage(PurchaseMessage purchaseMessage) {

		messageService.sendAndSavePurchaseMessage(purchaseMessage);
	}

	@ResponseBody
	@GetMapping("/api/message/history")
	public Response<ChatListResponse> findChatHistory(@RequestParam(value = "cursor", required = false) LocalDateTime cursor,
		@RequestParam("roomId") Long roomId, @RequestParam("size") Integer size) {

		return messageService.getChatHistoryWithCursor(roomId, cursor, size);
	}

}
