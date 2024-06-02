package com.shareit.shareit.chat.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.shareit.shareit.chat.domain.entity.ChatMessage;
import com.shareit.shareit.chat.domain.entity.PurchaseMessage;

public interface MessageRepository {

	void saveChatMessage(ChatMessage chatMessage);

	void savePurchaseMessage(PurchaseMessage purchaseMessage);

	List<Object> findAllDocument(String document, LocalDateTime cursor, Long roomId, int size);
}
