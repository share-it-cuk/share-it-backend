package com.shareit.shareit.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shareit.shareit.domain.Response;

@Controller
@ResponseBody
public class SecurityController {
	@GetMapping("/token/check")
	public Response tokenCheck() {
		return Response.ok();
	}
}
