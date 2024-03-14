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
		columnNames = "NICKNAME"
	)
})

public class Member extends BaseEntity {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Column(name = "email", length = 50)
	private String email;

	@Column(name = "password", length = 50)
	private String password;

	@Column(name = "nickname", length = 50)
	private String nickname;

	@Column(name = "phone_num", length = 50)
	private String phoneNum;

	@Column(name = "user_role")
	@Enumerated(EnumType.STRING)
	private Role userRole;

	@Column(name = "address")
	private String address;

	@Column(name = "profile_image")
	private String profileImage;

	@Column(name = "social_id")
	private String socialId;

	@Column(name = "refresh_token")
	private String refreshToken;

	@Column(name = "profile_key")
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
