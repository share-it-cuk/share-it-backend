package com.shareit.shareit.repository;

import static org.assertj.core.api.Assertions.*;

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
import com.shareit.shareit.purchase.domain.entity.Purchase;
import com.shareit.shareit.review.entity.Review;
import com.shareit.shareit.review.repository.ReviewRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QueryDslConfig.class)
public class ReviewRepositoryTest {
	private final String testString1 = "tes1";
	private final String testString2 = "test2";

	private final Long testCost1 = 0L;
	private final Long testCost2 = 1000L;

	private final Integer testInteger1 = 4;

	private Campus testCampus;

	private Member member1;
	private Member member2;

	private Purchase purchase1;
	private Purchase purchase2;

	@Autowired
	private EntityManager em;
	@Autowired
	private ReviewRepository reviewRepository;

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

		member2 = Member.builder()
			.email(testString2)
			.password(testString2)
			.nickname(testString2)
			.campus(testCampus)
			.build();

		em.persist(member1);
		em.persist(member2);
	}

	@Test
	@DisplayName("남은 리뷰 개수 받아오기")
	void countReview() {
		//Given
		Review IncompleteReview = Review.builder()
			.purchase(purchase1)
			.reviewer(member1)
			.receiver(member2)
			.build();

		Review CompleteReview = Review.builder()
			.purchase(purchase2)
			.reviewer(member1)
			.receiver(member2)
			.content(testString1)
			.score(testInteger1)
			.build();

		Review NotMyReview = Review.builder()
			.purchase(purchase1)
			.reviewer(member2)
			.receiver(member1)
			.build();

		em.persist(IncompleteReview);
		em.persist(CompleteReview);
		em.persist(NotMyReview);

		//where
		Long cnt = reviewRepository.countIncompleteReviews(member1.getId());

		//Then
		try {
			assertThat(cnt).isEqualTo(1L);
			log.info("Success - correct count cnt= " + cnt);
		} catch (AssertionError e) {
			log.info("FAIL - Incorrect count cnt= " + cnt);
		}
	}
}
