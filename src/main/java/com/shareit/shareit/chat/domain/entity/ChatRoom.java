package com.shareit.shareit.chat.domain.entity;

import java.util.HashSet;
import java.util.Set;

import com.shareit.shareit.post.domain.entity.Post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

	@Id @Column(name = "chat_room_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@Builder.Default
	@OneToMany(mappedBy = "chatRoom")
	private Set<ChatMember> chatMembers = new HashSet<>();

	public void updateTitle(String title) {
		this.title = title;
	}

	public void updateChatMember(ChatMember chatMember) {
		if (!this.chatMembers.contains(chatMember)){
			chatMembers.add(chatMember);
		}
		if (chatMember.getChatRoom() != this){
			chatMember.updateChatRoom(this);
		}
	}

}
