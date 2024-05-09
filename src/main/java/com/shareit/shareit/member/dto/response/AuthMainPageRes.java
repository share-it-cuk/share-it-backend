package com.shareit.shareit.member.dto.response;

import java.util.List;

import com.shareit.shareit.member.dto.etc.PostInfoListAtMain;
import com.shareit.shareit.member.dto.etc.ToTradeAtMain;
import com.shareit.shareit.member.dto.etc.UserInfoDto;
import com.shareit.shareit.post.domain.dto.response.PostInfoForList;

import lombok.Builder;

@Builder
public class AuthMainPageRes {

	UserInfoDto userInfoDto;

	List<ToTradeAtMain> toTradeList;

	List<PostInfoForList> recentPostInfoList;

	List<PostInfoListAtMain> myPostList;
}
