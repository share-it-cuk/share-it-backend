package com.shareit.shareit.purchase.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shareit.shareit.purchase.domain.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	@Query("SELECT p FROM Purchase p WHERE p.startDate >= :today OR p.endDate >= :today")
	Optional<List<Purchase>> getToTradeList(@Param("today") LocalDateTime today);
}
