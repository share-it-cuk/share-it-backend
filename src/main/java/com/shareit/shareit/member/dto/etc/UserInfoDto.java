package com.shareit.shareit.member.dto.etc;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInfoDto {
	private String username;
	private String campusName;
	private Long onTrade;
	private Long toReview;
	private Long nextReserve;
	//userProfileImage

	@Builder
	UserInfoDto(String username, String campusName, Long onTrade, Long toReview, Long nextReserve) {
		this.username = username;
		this.campusName = campusName;
		this.onTrade = onTrade;
		this.toReview = toReview;
		this.nextReserve = nextReserve;
	}

}
