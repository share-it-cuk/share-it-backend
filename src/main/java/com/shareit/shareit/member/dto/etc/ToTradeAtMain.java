package com.shareit.shareit.member.dto.etc;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToTradeAtMain {
	private LocalDateTime date;
	private String type;
	private String title;

	@Builder
	ToTradeAtMain(LocalDateTime date, String type, String title) {
		this.date = date;
		this.type = type;
		this.title = title;

	}
}
