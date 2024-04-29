package com.shareit.shareit.member.util;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.shareit.shareit.domain.entity.Campus;
import com.shareit.shareit.member.domain.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ContextUserInfo extends User {

	private final Campus campus;
	private final Member member;

	@Builder(builderMethodName = "contextUserInfoBuilder")
	public ContextUserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities,
		Campus campus, Member member) {
		super(username, password, authorities);

		this.member = member;
		this.campus = campus;

	}
}
