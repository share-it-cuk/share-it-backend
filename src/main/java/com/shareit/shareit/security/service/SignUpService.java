package com.shareit.shareit.security.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shareit.shareit.Exceptions.BusinessException;
import com.shareit.shareit.domain.Repository.MemberRepository;
import com.shareit.shareit.domain.Response;
import com.shareit.shareit.domain.ResponseCode;
import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.member.util.Role;
import com.shareit.shareit.security.dto.AccountReq;
import com.shareit.shareit.security.dto.AccountRes;
import com.shareit.shareit.security.dto.DuplicationRes;
import com.shareit.shareit.security.dto.LoginReq;
import com.shareit.shareit.security.jwt.JWTUtil;

@Service
public class SignUpService {

	private final MemberRepository memberRepository;

	private final JWTUtil jwtUtil;

	SignUpService(MemberRepository memberRepository, JWTUtil jwtUtil) {
		this.memberRepository = memberRepository;
		this.jwtUtil = jwtUtil;
	}

	public Response<AccountRes> generateAccount(AccountReq req) {
		String userId = req.getUserId();
		String password = req.getPassword();
		String uuid = UUID.randomUUID().toString();

		Member member = Member.builder()
			.userId(userId)
			.password(password)
			.uuid(uuid)
			.userRole(Role.ROLE_SOCIAL)
			.build();

		memberRepository.save(member);
		String token = jwtUtil.createJwt(uuid, Role.ROLE_SOCIAL.getRole(), 60 * 600 * 600L);

		return Response.ok(new AccountRes(token));
	}

	public Response<DuplicationRes> checkIdDuplication(String userId) {
		Boolean isDuplicate = memberRepository.existsByUserId(userId);

		return Response.ok(
			new DuplicationRes(isDuplicate)
		);
	}

	public Response<AccountRes> login(LoginReq req) {
		String userId = req.getUserId();
		String password = req.getPassword();

		Member member = memberRepository.findMemberByUserId(userId);

		if (member == null) {
			throw new BusinessException(ResponseCode.USER_ID_NOT_AVAILABLE);
		}

		if (member.getPassword().equals(password)) {
			String uuid = member.getUuid();
			Role role = member.getUserRole();
			String token = jwtUtil.createJwt(uuid, role.getRole(), 60 * 600 * 600L);

			return Response.ok(new AccountRes(token));
		} else {
			throw new BusinessException(ResponseCode.USER_PASSWORD_NOT_AVAILABLE);
		}
	}
}
