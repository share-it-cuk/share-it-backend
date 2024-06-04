package com.shareit.shareit.member.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.shareit.shareit.member.dto.etc.PostInfoListAtMain;
import com.shareit.shareit.member.dto.etc.ToTradeAtMain;
import com.shareit.shareit.member.dto.etc.UserInfoDto;
import com.shareit.shareit.post.domain.dto.response.PostInfoForList;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthMainPageRes {

	@JsonProperty("user_info")
	UserInfoDto userInfoDto;

	List<ToTradeAtMain> toTradeList;

	List<PostInfoForList> recentPostInfoList;
	
	List<PostInfoListAtMain> myPostList;
}
