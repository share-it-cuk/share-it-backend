package com.shareit.shareit.chat.service;

import java.time.LocalDateTime;

import com.shareit.shareit.chat.domain.entity.ChatMessage;
import com.shareit.shareit.chat.domain.entity.PurchaseMessage;
import com.shareit.shareit.chat.domain.response.ChatListResponse;
import com.shareit.shareit.domain.Response;

public interface MessageService {

	void sendAndSaveMessage(ChatMessage chatMessage);

	void sendAndSavePurchaseMessage(PurchaseMessage purchaseMessage);

	Response<ChatListResponse> getChatHistoryWithCursor(Long chatRoomId, LocalDateTime cursor, int size);

}
