package com.shareit.shareit.domain.entity;

import com.shareit.shareit.domain.BaseEntity;
import com.shareit.shareit.domain.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member", uniqueConstraints = {
	@UniqueConstraint(
		name = "NICKNAME_UNIQUE",
		columnNames = "nickname"
	)
})

public class Member extends BaseEntity {

	@Id
	@Column(name = "member_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String email;

	private String password;

	@Column(length = 50)
	private String nickname;

	@Column(length = 50)
	private String phoneNum;

	@Enumerated(EnumType.STRING)
	private Role userRole;

	private String address;

	private String profileImage;

	private String socialId;

	private String refreshToken;

	private String profileKey;

	@ManyToOne
	@JoinColumn(name = "campus_id")
	private Campus campus;

	@Builder
	public Member(String email, String password, String nickname, String phoneNum, Role userRole, String address,
		String profileImage, String socialId, String refreshToken, String profileKey) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.phoneNum = phoneNum;
		this.userRole = userRole;
		this.address = address;
		this.profileImage = profileImage;
		this.socialId = socialId;
		this.refreshToken = refreshToken;
		this.profileKey = profileKey;
	}

}
