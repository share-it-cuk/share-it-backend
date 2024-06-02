package com.shareit.shareit.security.dto;

import java.util.Map;

import lombok.Data;

@Data
public class KakaoResponse implements OAuth2Response {
	private final Map<String, Object> attributes;
	private Map<String, Object> kakaoAccountAttributes;
	private Map<String, Object> profileAttributes;

	public KakaoResponse(Map<String, Object> attributes) {
		this.attributes = attributes;
		this.kakaoAccountAttributes = (Map<String, Object>)attributes.get("kakao_account");
		this.profileAttributes = (Map<String, Object>)kakaoAccountAttributes.get("profile");
	}

	@Override
	public String getProvider() {
		return "kakao";
	}

	@Override
	public String getProviderId() {
		return attributes.get("id").toString();
	}

	@Override
	public String getEmail() {
		return kakaoAccountAttributes.get("profile_nickname").toString();
	}
}
