package com.shareit.shareit.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shareit.shareit.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("SELECT COUNT(r) from Review r where r.reviewer.id = :reviewerId AND (r.content IS NULL OR TRIM(r.content) = '')")
	Long countByReviewerIs(@Param("reviewerId") Long reviewerId);
}
