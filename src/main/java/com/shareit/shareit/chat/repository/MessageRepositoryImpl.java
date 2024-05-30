package com.shareit.shareit.chat.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.shareit.shareit.chat.domain.entity.ChatMessage;
import com.shareit.shareit.chat.domain.entity.PurchaseMessage;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepository {

	private final MongoTemplate mongoTemplate;

	@Override
	public void saveChatMessage(ChatMessage chatMessage) {
		mongoTemplate.save(chatMessage, "message");
	}

	@Override
	public void savePurchaseMessage(PurchaseMessage purchaseMessage) {
		mongoTemplate.save(purchaseMessage, "message");
	}

	@Override
	public List<Object> findAllDocument(String document, LocalDateTime cursor, Long roomId, int size) {

		Query query = new Query();
		query.addCriteria(Criteria.where("roomId").is(roomId));

		if (cursor != null){
			query.addCriteria(Criteria.where("sendTime").lt(cursor));
		}
		query.limit(size);
		query.with(Sort.by(Sort.Direction.DESC, "sendTime"));

		return mongoTemplate.find(query, Object.class, document);
	}
}
