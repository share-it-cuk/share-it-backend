package com.shareit.shareit.security.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

	private final UserDto userDto;

	public CustomOAuth2User(UserDto userDto) {
		this.userDto = userDto;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				System.out.println(userDto.getRole());
				return userDto.getRole();
			}
		});
		return collection;
	}

	@Override
	public String getName() {
		return userDto.getUsername();
	}

	public String getUsername() {
		return userDto.getUsername();
	}

	public String getUuid() {
		return userDto.getUuid();
	}

	public String getRole() {
		return userDto.getRole();
	}

}
