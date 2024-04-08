package com.shareit.shareit.member.util;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.shareit.shareit.domain.entity.Campus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ContextUserInfo extends User {

	private final Long id;
	private final Campus campus;
	private final Role role;
	private final String profileImage;

	@Builder(builderMethodName = "contextUserInfoBuilder")
	public ContextUserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities,
		Long id, Campus campus, Role role, String profileImage) {
		super(username, password, authorities);
		this.id = id;
		this.campus = campus;
		this.profileImage = profileImage;
		this.role = role;
	}
}
