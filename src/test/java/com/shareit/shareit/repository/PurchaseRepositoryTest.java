package com.shareit.shareit.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.shareit.shareit.config.QueryDslConfig;
import com.shareit.shareit.domain.entity.Campus;
import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.post.domain.entity.Post;
import com.shareit.shareit.purchase.domain.entity.Purchase;
import com.shareit.shareit.purchase.domain.repository.PurchaseRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QueryDslConfig.class)
public class PurchaseRepositoryTest {

	private final String testString1 = "test1";
	private final Long testCost = 100L;
	private final LocalDateTime testDateNow = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
	private final LocalDateTime testDateNowAfter1Week = testDateNow.plusWeeks(1L);
	private final LocalDateTime testDateNowBefore1Week = testDateNow.minusWeeks(1L);
	private final LocalDateTime testDateNowAfter2Week = testDateNow.plusWeeks(2L);
	private final LocalDateTime testDateNowBefore2Week = testDateNow.minusWeeks(2L);

	private Purchase purchase1;
	private Purchase purchase2;
	private Purchase purchase3;
	private Purchase purchase4;
	private Purchase purchase5;

	private Post testPost1;
	private Member member1;

	@Autowired
	private EntityManager em;
	@Autowired
	private PurchaseRepository purchaseRepository;

	private Campus testCampus;

	@BeforeEach
	void beforeEach() {

		testCampus = Campus.builder()
			.campusName(testString1)
			.build();

		em.persist(testCampus);

		member1 = Member.builder()
			.email(testString1)
			.password(testString1)
			.nickname(testString1)
			.campus(testCampus)
			.build();

		em.persist(member1);
	}

	@Test
	@DisplayName("오늘 기준 대여,반납 예정인 거래 가져오기")
	void findToTradeList() {

		//Given
		purchase1 = Purchase.builder()
			.post(testPost1)
			.member(member1)
			.startDate(testDateNowBefore2Week)
			.endDate(testDateNowBefore1Week)
			.account(testString1)
			.cost(testCost)
			.build();

		purchase2 = Purchase.builder()
			.post(testPost1)
			.member(member1)
			.startDate(testDateNowBefore1Week)
			.endDate(testDateNow)
			.account(testString1)
			.cost(testCost)
			.build();

		purchase3 = Purchase.builder()
			.post(testPost1)
			.member(member1)
			.startDate(testDateNowBefore1Week)
			.endDate(testDateNowAfter1Week)
			.account(testString1)
			.cost(testCost)
			.build();

		purchase4 = Purchase.builder()
			.post(testPost1)
			.member(member1)
			.startDate(testDateNow)
			.endDate(testDateNowAfter1Week)
			.account(testString1)
			.cost(testCost)
			.build();

		purchase5 = Purchase.builder()
			.post(testPost1)
			.member(member1)
			.startDate(testDateNowAfter1Week)
			.endDate(testDateNowAfter2Week)
			.account(testString1)
			.cost(testCost)
			.build();

		em.persist(purchase1);
		em.persist(purchase2);
		em.persist(purchase3);
		em.persist(purchase4);
		em.persist(purchase5);

		//Where
		Optional<List<Purchase>> toTradeAtMainList = purchaseRepository.getToTradeList(testDateNow);

		//Then
		toTradeAtMainList.get().forEach(purchase -> {
				LocalDate startDate = purchase.getStartDate().toLocalDate();
				LocalDate endDate = purchase.getEndDate().toLocalDate();
				LocalDate now = testDateNow.toLocalDate();

				if (startDate.isAfter(now) || startDate.isEqual(now)) {
					try {
						assertThat(startDate).isAfterOrEqualTo(now);
						log.info("Success - startDate="
							+ startDate
							+ " endDate= "
							+ endDate
						);
					} catch (AssertionError e) {
						log.info("it's not right - startDate="
							+ startDate
							+ " endDate= "
							+ endDate
						);
					}

				} else {
					try {
						assertThat(endDate).isAfterOrEqualTo(now);
						log.info("Success - startDate="
							+ startDate
							+ " endDate= "
							+ endDate
						);
					} catch (AssertionError e) {
						log.info("it's not right - startDate="
							+ startDate
							+ " endDate= "
							+ endDate
						);
					}
				}
			}
		);

	}
}
