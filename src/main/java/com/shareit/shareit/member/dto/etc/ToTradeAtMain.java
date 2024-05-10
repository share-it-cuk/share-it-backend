package com.shareit.shareit.member.dto.etc;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToTradeAtMain {
	LocalDateTime date;
	String type;
	String title;
}
