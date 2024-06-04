package com.shareit.shareit.domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.shareit.shareit.domain.entity.Campus;

public interface CampusRepository extends JpaRepository<Campus, Long> {
	Campus findByCampusName(@Param("campusName") String campusName);
}
