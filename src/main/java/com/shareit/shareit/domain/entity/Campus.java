package com.shareit.shareit.domain.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.shareit.shareit.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "campus")
@SQLDelete(sql = "UPDATE campus SET active_status = 'DELETED' WHERE campus_id = ?")
@SQLRestriction("active_status <> 'DELETED' and active_status <> 'PENDING'")
public class Campus extends BaseEntity {
	@Id
	@Column(name = "campus_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String campusName;

	@Builder
	public Campus(String campusName) {
		this.campusName = campusName;

	}
}
