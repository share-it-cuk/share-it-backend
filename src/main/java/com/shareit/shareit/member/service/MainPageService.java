package com.shareit.shareit.member.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shareit.shareit.domain.Response;
import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.member.dto.etc.PostInfoListAtMain;
import com.shareit.shareit.member.dto.etc.ToTradeAtMain;
import com.shareit.shareit.member.dto.etc.UserInfoDto;
import com.shareit.shareit.member.dto.response.AuthMainPageRes;
import com.shareit.shareit.member.dto.response.BasicMainPageRes;
import com.shareit.shareit.post.domain.dto.response.PostInfoForList;
import com.shareit.shareit.post.domain.entity.Post;
import com.shareit.shareit.post.repository.PostRepository;
import com.shareit.shareit.purchase.domain.entity.Purchase;
import com.shareit.shareit.purchase.domain.repository.PurchaseRepository;
import com.shareit.shareit.review.repository.ReviewRepository;
import com.shareit.shareit.utils.SecurityUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MainPageService {

	private final SecurityUtils securityUtils;
	private final PostRepository postRepository;
	private final PurchaseRepository purchaseRepository;
	private final ReviewRepository reviewRepository;

	public Response<BasicMainPageRes> getBasicMain() {
		Optional<List<Post>> optionalPosts = postRepository.findRecentTop3Post();

		List<PostInfoForList> recentPostInfoList = optionalPosts
			.map(posts -> posts.stream()
				.map(PostInfoForList::fromEntity)
				.toList())
			.orElse(Collections.emptyList());

		BasicMainPageRes res = new BasicMainPageRes(recentPostInfoList);
		return Response.ok(res);
	}

	public Response<AuthMainPageRes> getAuthMain() {
		//Authentication Get UserInfo
		Member currentUserInfo = securityUtils.getMember();

		LocalDateTime now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		Optional<List<Purchase>> myAppointments = purchaseRepository.getToTradeList(now);
		List<ToTradeAtMain> toTradeAtMainList = new ArrayList<>(3);
		Long onTrade = myAppointments.map(List::size).map(Long::valueOf).orElse(0L);

		//최근 3개의 대여일 or 반납일만 추출
		myAppointments.ifPresent(myAppointmentList -> {
			LocalDate today = LocalDate.now();
			myAppointmentList.stream()
				.limit(3)
				.forEach(p -> {
					LocalDate startDate = p.getStartDate().toLocalDate();
					String type = startDate.isAfter(today) || startDate.isEqual(today) ? "대여일" : "반납일";
					LocalDateTime date =
						startDate.isAfter(today) || startDate.isEqual(today) ? p.getStartDate() : p.getEndDate();

					toTradeAtMainList.add(
						ToTradeAtMain.builder()
							.date(date)
							.type(type)
							.title(p.getPost().getTitle())
							.build()
					);
				});
		});

		Optional<List<Post>> optionalPosts = postRepository.findRecentTop3Post();
		Optional<List<Post>> optionalMyPostList = postRepository.findPostsById(currentUserInfo.getId());

		List<PostInfoForList> recentPostInfoList = optionalPosts
			.map(posts -> posts.stream()
				.map(PostInfoForList::fromEntity)
				.toList())
			.orElse(Collections.emptyList());

		List<PostInfoListAtMain> postInfoListAtMain = optionalMyPostList
			.map(posts -> posts.stream()
				.map(PostInfoListAtMain::fromEntity)
				.toList())
			.orElse(Collections.emptyList());

		return Response.ok(
			AuthMainPageRes.builder()
				.userInfoDto(
					UserInfoDto.builder()
						.username(currentUserInfo.getNickname())
						.campusName(currentUserInfo.getCampus().getCampusName())
						.onTrade(onTrade)
						.nextReserve(3L)
						.toReview(reviewRepository.countIncompleteReviews(currentUserInfo.getId()))
						.build()
				)
				.toTradeList(toTradeAtMainList)
				.recentPostInfoList(recentPostInfoList)
				.myPostList(postInfoListAtMain)
				.build()
		);
	}

}
