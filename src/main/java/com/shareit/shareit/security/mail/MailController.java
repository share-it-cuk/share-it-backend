package com.shareit.shareit.security.mail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shareit.shareit.domain.Response;
import com.shareit.shareit.security.mail.dto.AuthMailReq;
import com.shareit.shareit.security.mail.dto.RegisterMailReq;
import com.shareit.shareit.security.mail.dto.RegisterMailRes;

@Controller
@ResponseBody
public class MailController {

	private final MailService mailService;

	MailController(MailService mailService) {
		this.mailService = mailService;
	}

	@PostMapping("/auth/mail")
	public Response<RegisterMailRes> registerMail(@RequestBody RegisterMailReq req) {
		return mailService.requestMail(req);
	}

	@PostMapping("/auth/email-code")
	public Response authMail(@RequestBody AuthMailReq req) {
		return mailService.authMail(req);
	}
}
