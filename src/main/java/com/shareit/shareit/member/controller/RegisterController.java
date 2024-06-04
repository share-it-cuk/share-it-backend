package com.shareit.shareit.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shareit.shareit.domain.Response;
import com.shareit.shareit.member.dto.request.RegisterReq;
import com.shareit.shareit.member.dto.response.RegisterRes;
import com.shareit.shareit.member.service.RegisterService;

@Controller
@ResponseBody
public class RegisterController {

	private final RegisterService registerService;

	RegisterController(RegisterService registerService) {
		this.registerService = registerService;
	}

	@PostMapping("/auth/register")
	public Response<RegisterRes> registerMember(@RequestBody RegisterReq req) {
		return registerService.registerMember(req);
	}
}
