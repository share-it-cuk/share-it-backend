package com.shareit.shareit.member.dto.etc;

import lombok.Builder;

@Builder
public class UserInfoDto {
	String username;
	String campusName;
	Long onTrade;
	Long toReview;
	Long nextReserve;
	//userProfileImage

}
