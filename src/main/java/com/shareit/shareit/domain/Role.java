package com.shareit.shareit.domain;

import lombok.Getter;

@Getter
public enum Role {
	ROLE_LENDER("ROLE_USER"),
	ROLE_GRABBER("ROLE_GRABBER"),
	ROLE_ADMIN("ROLE_ADMIN");

	String role;

	Role(String role) {
		this.role = role;
	}

	public String value() {
		return role;
	}
}
