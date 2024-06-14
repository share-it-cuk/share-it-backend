package com.shareit.shareit.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shareit.shareit.security.dto.CustomOAuth2User;
import com.shareit.shareit.security.dto.UserDto;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {

	private final JWTUtil jwtUtil;

	public JwtFilter(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String requestURI = request.getRequestURI();
		if (requestURI.matches("/sub/.*") ||
			requestURI.matches("/pub/.*") ||
			requestURI.matches("/ws/.*") ||
			requestURI.matches("/account/.*") ||
			requestURI.matches("/api/message/.*")) {
			filterChain.doFilter(request, response);
			return;
		}

		String authorization = request.getHeader("Authorization");

		//Authorization 헤더 검증
		if (authorization == null || authorization.isBlank() || authorization.isEmpty() || authorization.equals(
			"undefined")) {
			//System.out.println("[JwtFilter] - token is blank");
			throw new JwtException("INVALID");
		}

		//System.out.println("[filterchain] - 널검증 만료검증 사이");

		//토큰
		String token = authorization;
		//토큰 소멸 시간 검증
		if (jwtUtil.isExpired(token)) {
			//System.out.println("token expired");
			throw new JwtException("EXPIRE");
		}

		//토큰에서 username과 role 획득
		String uuid = jwtUtil.getUuid(token);
		String role = jwtUtil.getRole(token);

		//userDTO를 생성하여 값 set
		UserDto userDTO = new UserDto();
		userDTO.setRole(role);
		userDTO.setUuid(uuid);

		//UserDetails에 회원 정보 객체 담기
		CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

		//스프링 시큐리티 인증 토큰 생성
		Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null,
			customOAuth2User.getAuthorities());

		//세션에 사용자 등록
		SecurityContextHolder.getContext().setAuthentication(authToken);
		filterChain.doFilter(request, response);
	}
}
