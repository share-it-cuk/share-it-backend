package com.shareit.shareit.domain.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.shareit.shareit.member.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findById(@Param("id") Long id);

	Member findMemberByUuid(@Param("uuid") String uuid);
}
