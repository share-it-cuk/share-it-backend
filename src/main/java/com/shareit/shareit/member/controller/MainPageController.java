package com.shareit.shareit.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shareit.shareit.domain.Response;
import com.shareit.shareit.member.dto.response.AuthMainPageRes;
import com.shareit.shareit.member.dto.response.BasicMainPageRes;
import com.shareit.shareit.member.service.MainPageService;

@RestController
public class MainPageController {

	private final MainPageService myPageService;

	MainPageController(MainPageService mainPageService) {
		this.myPageService = mainPageService;
	}

	@GetMapping("/main/auth")
	public Response<AuthMainPageRes> getAuthMain() {
		return myPageService.getAuthMain();
	}

	@GetMapping("/main/basic")
	public Response<BasicMainPageRes> getBasicMain() {
		return myPageService.getBasicMain();
	}
}
