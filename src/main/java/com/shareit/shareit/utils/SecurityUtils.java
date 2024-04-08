package com.shareit.shareit.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.shareit.shareit.member.util.ContextUserInfo;

@Component
public class SecurityUtils {

	public ContextUserInfo getContextUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return null;
		}

		Object principal = authentication.getPrincipal();

		if (principal instanceof ContextUserInfo) {
			return (ContextUserInfo)principal;
		}

		return null;
	}

}
