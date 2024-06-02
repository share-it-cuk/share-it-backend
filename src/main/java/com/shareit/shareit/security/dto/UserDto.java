package com.shareit.shareit.security.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor

public class UserDto {
	private String role;
	private String username;
	private String uuid;

	@Builder
	public UserDto(String role, String username, String uuid) {
		this.role = role;
		this.username = username;
		this.uuid = uuid;
	}
}
