package com.shareit.shareit.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shareit.shareit.domain.Response;
import com.shareit.shareit.security.dto.AccountReq;
import com.shareit.shareit.security.dto.AccountRes;
import com.shareit.shareit.security.dto.DuplicationRes;
import com.shareit.shareit.security.dto.LoginReq;
import com.shareit.shareit.security.service.SignUpService;

@RestController
public class SignUpController {

	SignUpService signUpService;

	SignUpController(SignUpService signUpService) {
		this.signUpService = signUpService;
	}

	@PostMapping("/account/signup")
	@ResponseBody
	public Response<AccountRes> generateAccount(@RequestBody AccountReq req) {
		return signUpService.generateAccount(req);
	}

	@GetMapping("/account/check-duplication")
	public Response<DuplicationRes> checkIdDuplication(@RequestParam String userId) {
		return signUpService.checkIdDuplication(userId);
	}

	@PostMapping("/account/login")
	public Response<AccountRes> login(@RequestBody LoginReq req) {
		return signUpService.login(req);
	}
}
