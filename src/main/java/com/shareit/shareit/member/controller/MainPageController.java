package com.shareit.shareit.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.shareit.shareit.domain.Response;
import com.shareit.shareit.member.dto.response.AuthMainPageRes;
import com.shareit.shareit.member.service.MainPageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainPageController {

	private final MainPageService myPageService;

	@GetMapping("/main-auth")
	public Response<AuthMainPageRes> userInfo() {

		return myPageService.authMain();
	}

}
