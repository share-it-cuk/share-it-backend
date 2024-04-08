package com.shareit.shareit.domain.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.shareit.shareit.domain.entity.Campus;
import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.member.util.Role;

@Component
public class TempContextHolder {

	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	Member member = (Member)authentication.getPrincipal();

	public Long getMemberId() {
		return member.getId();
	}

	public Campus getCampus() {
		return member.getCampus();
	}

	public String getNickname() {
		return member.getNickname();
	}

	public Role getUserRole() {
		return member.getUserRole();
	}

	public String getProfileKey() {
		return member.getProfileKey();
	}
}

