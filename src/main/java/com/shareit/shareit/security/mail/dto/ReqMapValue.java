package com.shareit.shareit.security.mail.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReqMapValue {
	String code;
	LocalDateTime putTime;
}
