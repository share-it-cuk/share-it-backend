package com.shareit.shareit.domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shareit.shareit.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
