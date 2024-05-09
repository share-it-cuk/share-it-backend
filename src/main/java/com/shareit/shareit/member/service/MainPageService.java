package com.shareit.shareit.member.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shareit.shareit.domain.Repository.MemberRepository;
import com.shareit.shareit.domain.Response;
import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.member.dto.etc.PostInfoListAtMain;
import com.shareit.shareit.member.dto.etc.ToTradeAtMain;
import com.shareit.shareit.member.dto.etc.UserInfoDto;
import com.shareit.shareit.member.dto.response.AuthMainPageRes;
import com.shareit.shareit.post.domain.dto.response.PostInfoForList;
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
	private final MemberRepository memberRepository;
	private final PostRepository postRepository;
	private final PurchaseRepository purchaseRepository;
	private final ReviewRepository reviewRepository;

	public Response<AuthMainPageRes> authMain() {
		//Authentication Get UserInfo

		Member currentUserInfo = memberRepository.findById(0L).orElseThrow();

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

		return Response.ok(
			AuthMainPageRes.builder()
				.userInfoDto(
					UserInfoDto.builder()
						.username(currentUserInfo.getNickname())
						.campusName(currentUserInfo.getCampus().getCampusName())
						.onTrade(onTrade)
						.nextReserve(3L)
						.toReview(reviewRepository.countByReviewerIs(currentUserInfo.getId()))
						.build()
				)
				.toTradeList(toTradeAtMainList)
				.recentPostInfoList(postRepository.findRecentTop3Post()
					.stream()
					.map(PostInfoForList::fromEntity)
					.toList()
				)
				.myPostList(postRepository.findPostById(currentUserInfo.getId())
					.stream()
					.map(PostInfoListAtMain::fromEntity)
					.toList())
				.build()
		);
	}
}
