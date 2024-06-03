package com.shareit.shareit.member.service;

import org.springframework.stereotype.Service;

import com.shareit.shareit.domain.Repository.MemberRepository;
import com.shareit.shareit.domain.Response;
import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.member.dto.request.RegisterReq;
import com.shareit.shareit.member.dto.response.RegisterRes;
import com.shareit.shareit.member.util.Role;
import com.shareit.shareit.security.SecurityUtil;
import com.shareit.shareit.security.jwt.JWTUtil;

import jakarta.transaction.Transactional;

@Service
public class RegisterService {
	private final SecurityUtil securityUtil;

	private final MemberRepository memberRepository;
	private final JWTUtil jwtUtil;

	RegisterService(SecurityUtil securityUtil, MemberRepository memberRepository, JWTUtil jwtUtil) {
		this.securityUtil = securityUtil;
		this.memberRepository = memberRepository;
		this.jwtUtil = jwtUtil;
	}

	@Transactional
	public Response<RegisterRes> registerMember(RegisterReq req) {
		Member member = securityUtil.getMember();
		String uuid = member.getUuid();
		member.updateRole(Role.ROLE_USER);
	
		String token = jwtUtil.createJwt(uuid, Role.ROLE_USER.getRole(), 60 * 600 * 600L);

		return Response.ok(new RegisterRes(token));
	}

}
