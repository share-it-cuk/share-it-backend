package com.shareit.shareit.chat.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shareit.shareit.chat.domain.entity.ChatMessage;
import com.shareit.shareit.chat.domain.entity.PurchaseMessage;
import com.shareit.shareit.chat.domain.response.ChatListResponse;
import com.shareit.shareit.chat.repository.MessageRepository;
import com.shareit.shareit.domain.Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

	private final MessageRepository messageRepository;
	private final SimpMessagingTemplate template;

	@Override
	public void sendAndSaveMessage(ChatMessage chatMessage) {

		template.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);

		messageRepository.saveChatMessage(chatMessage);

		log.info("chatMessage receive={}", chatMessage.getMessage());

	}

	@Override
	public void sendAndSavePurchaseMessage(PurchaseMessage purchaseMessage) {

		template.convertAndSend("/sub/chat/room/" + purchaseMessage.getRoomId(), purchaseMessage);

		messageRepository.savePurchaseMessage(purchaseMessage);
	}

	@Override
	public Response<ChatListResponse> getChatHistoryWithCursor(Long chatRoomId, LocalDateTime cursor, int size) {
		List<Object> message = messageRepository.findAllDocument("message", cursor, chatRoomId, size + 1);

		boolean hasNext = true;

		if (message.size() <= size){
			hasNext = false;
		}

		if (message.isEmpty()) {
			return Response.ok(ChatListResponse.builder()
				.hasNext(hasNext)
				.cursor(cursor)
				.build());
		}

		int lastIndex = message.size() > size ? size -2 : message.size() - 1;
		LocalDateTime lastCursor;

		if (message.get(lastIndex) instanceof ChatMessage temporaryChat) {
			lastCursor = temporaryChat.getSendTime();
		} else {
			PurchaseMessage temporaryChat = (PurchaseMessage)message.get(lastIndex);
			lastCursor = temporaryChat.getSendTime();
		}

		log.info("size={}", message.size());

		return Response.ok(
			ChatListResponse.builder()
				.hasNext(hasNext)
				.cursor(lastCursor)
				.messages(message.stream()
					.limit(message.size())
					.toList()
				)
				.build()
		);
	}
}
