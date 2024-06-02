package com.shareit.shareit.security.service;

import java.util.UUID;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.member.domain.repository.MemberRepository;
import com.shareit.shareit.member.util.Role;
import com.shareit.shareit.security.dto.CustomOAuth2User;
import com.shareit.shareit.security.dto.KakaoResponse;
import com.shareit.shareit.security.dto.OAuth2Response;
import com.shareit.shareit.security.dto.UserDto;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private final MemberRepository memberRepository;

	public CustomOAuth2UserService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		System.out.println(oAuth2User.getName());

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2Response oAuth2Response = null;

		if (registrationId.equals("kakao")) {
			oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
		} else {
			return null;
		}

		String username = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();

		Member existData = memberRepository.findMemberBySocialId(username);
		UserDto userDto;
		if (existData == null) {
			String uuid = UUID.randomUUID().toString();
			Member member = Member.builder()
				.userRole(Role.ROLE_SOCIAL)
				.socialId(username)
				.uuid(uuid)
				.build();

			memberRepository.save(member);

			userDto = new UserDto().builder()
				.username(username)
				.role(Role.ROLE_SOCIAL.getRole())
				.uuid(uuid)
				.build();

		} else {
			existData.updateSocialId(username);
			memberRepository.save(existData);

			userDto = new UserDto().builder()
				.username(username)
				.role(existData.getUserRole().getRole())
				.uuid(existData.getUuid())
				.build();
		}

		System.out.println(userDto.getRole());
		return new CustomOAuth2User(userDto);
	}
}
