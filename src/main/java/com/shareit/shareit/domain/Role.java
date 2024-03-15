package com.shareit.shareit.domain;

import lombok.Getter;

@Getter
public enum Role {
	ROLE_USER("ROLE_USER"),
	ROLE_SOCIAL("ROLE_SOCIAL");

	String role;

	Role(String role) {
		this.role = role;
	}

	public String value() {
		return role;
	}
}
