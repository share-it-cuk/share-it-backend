package com.shareit.shareit.chat.domain.entity;

import com.shareit.shareit.member.domain.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(
	uniqueConstraints = {
		@UniqueConstraint(name = "UniqueMemberPerChatRoom", columnNames = {"member_id", "chat_room_id"})
	}
)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMember {

	@Id @Column(name = "chat_member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "chat_room_id")
	private ChatRoom chatRoom;

	public void updateChatRoom(ChatRoom chatRoom) {
		if (this.chatRoom != chatRoom){
			this.chatRoom = chatRoom;
		}
		if (!chatRoom.getChatMembers().contains(this)){
			chatRoom.updateChatMember(this);
		}
	}

}
