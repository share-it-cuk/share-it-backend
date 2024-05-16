package com.shareit.shareit.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shareit.shareit.chat.domain.entity.ChatMember;
import com.shareit.shareit.member.domain.entity.Member;

public interface ChatMemberRepository extends JpaRepository<ChatMember, Long> {

	@EntityGraph(attributePaths = {"chatRoom"})
	@Query("select cm from ChatMember cm where cm.member=:member")
	List<ChatMember> findAllChatMemberWithChatRoom(@Param("member") Member member);
}
