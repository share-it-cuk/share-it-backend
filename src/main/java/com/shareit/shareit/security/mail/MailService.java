package com.shareit.shareit.security.mail;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.shareit.shareit.Exceptions.BusinessException;
import com.shareit.shareit.domain.Repository.CampusRepository;
import com.shareit.shareit.domain.Repository.MemberRepository;
import com.shareit.shareit.domain.Response;
import com.shareit.shareit.domain.ResponseCode;
import com.shareit.shareit.domain.entity.Campus;
import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.member.util.Role;
import com.shareit.shareit.security.SecurityUtil;
import com.shareit.shareit.security.mail.dto.AuthMailReq;
import com.shareit.shareit.security.mail.dto.RegisterMailReq;
import com.shareit.shareit.security.mail.dto.RegisterMailRes;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
public class MailService {
	private final JavaMailSender javaMailSender;
	private final SecurityUtil securityUtil;
	private final MemberRepository memberRepository;
	private final MailRequestHandler mailRequestHandler;

	private final CampusRepository campusRepository;

	private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
	private static final int CODE_LENGTH = 6;

	@Value("${mail.sender}")
	private String sender;

	@Value("${spring.random.seed}")
	private String seed;

	MailService(JavaMailSender javaMailSender, SecurityUtil securityUtil, MemberRepository memberRepository,
		MailRequestHandler mailRequestHandler, CampusRepository campusRepository) {
		this.javaMailSender = javaMailSender;
		this.securityUtil = securityUtil;
		this.memberRepository = memberRepository;
		this.mailRequestHandler = mailRequestHandler;
		this.campusRepository = campusRepository;
	}

	@Transactional
	public Response<RegisterMailRes> requestMail(RegisterMailReq req) {
		Member member = securityUtil.getMember();
		String code = createCode();
		String email = req.getEmail();
		String uuid = member.getUuid();
		mailRequestHandler.addRequest(uuid, code);
		member.updateEmail(email);

		try {
			MimeMessage message = createEmail(email, code);
			javaMailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.ok(new RegisterMailRes(code));
	}

	@Transactional
	public Response authMail(AuthMailReq req) {
		String code = req.getCode();
		Member member = securityUtil.getMember();
		String uuid = member.getUuid();
		Boolean isSuccess = mailRequestHandler.checkRequest(uuid, code);
		Campus campus = campusRepository.findByCampusName("Catholic University of Korea");

		if (isSuccess) {
			member.updateRole(Role.ROLE_USER);
			member.addCampus(campus);
			mailRequestHandler.removeRequest(uuid);
		} else {
			throw new BusinessException(ResponseCode.MAIL_NOT_AVAILABLE);
		}
		return Response.ok();
	}

	public MimeMessage createEmail(String email, String code) throws MessagingException, UnsupportedEncodingException {

		String setFrom = sender;
		String toEmail = email;
		String title = "[share-it] 회원가입 이메일 인증";

		MimeMessage message = javaMailSender.createMimeMessage();

		message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
		message.setSubject(title);

		// 메일 내용 설정
		String msgOfEmail = "";
		msgOfEmail += "<h1> " + code + " </h1>";

		message.setFrom(setFrom);
		message.setText(msgOfEmail, "utf-8", "html");

		return message;
	}

	private String createCode() {
		long currentTimeMillis = System.currentTimeMillis();
		String combinedSeed = seed + currentTimeMillis;
		long hashedSeed = combinedSeed.hashCode();
		Random random = new Random(hashedSeed);
		StringBuilder code = new StringBuilder(CODE_LENGTH);

		for (int i = 0; i < CODE_LENGTH; i++) {
			int randomIndex = random.nextInt(CHARACTERS.length());
			char randomChar = CHARACTERS.charAt(randomIndex);
			code.append(randomChar);
		}
		return code.toString();
	}
}
