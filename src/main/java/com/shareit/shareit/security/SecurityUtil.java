package com.shareit.shareit.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.member.domain.repository.MemberRepository;
import com.shareit.shareit.security.dto.CustomOAuth2User;

@Component
public class SecurityUtil {

	MemberRepository memberRepository;

	SecurityUtil(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public CustomOAuth2User getContextUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return null;
		}

		Object principal = authentication.getPrincipal();

		if (principal instanceof CustomOAuth2User) {
			return (CustomOAuth2User)principal;
		}

		return null;
	}

	public Member getMember() {
		CustomOAuth2User user = getContextUserInfo();

		if (user == null) {
			return null;
		}

		String uuid = user.getUuid();
		return memberRepository.findMemberByUuid(uuid);
	}
}
