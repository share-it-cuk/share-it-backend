package com.shareit.shareit.domain.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.shareit.shareit.domain.Repository.CampusRepository;
import com.shareit.shareit.domain.entity.Campus;

import jakarta.transaction.Transactional;

@Component
class InitRun implements CommandLineRunner {

	private final CampusRepository campusRepository;

	InitRun(CampusRepository campusRepository) {
		this.campusRepository = campusRepository;
	}

	@Override
	@Transactional
	public void run(String... args) {
		Campus campus = Campus.builder()
			.campusName("Catholic University of Korea")
			.build();

		campusRepository.save(campus);
	}

}
