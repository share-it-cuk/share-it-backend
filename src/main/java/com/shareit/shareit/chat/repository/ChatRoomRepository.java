package com.shareit.shareit.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shareit.shareit.chat.domain.entity.ChatRoom;
import com.shareit.shareit.post.domain.entity.Post;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

	@EntityGraph(attributePaths = {"chatMembers", "chatMembers.member"})
	List<ChatRoom> findChatRoomsByPost(Post post);

}
