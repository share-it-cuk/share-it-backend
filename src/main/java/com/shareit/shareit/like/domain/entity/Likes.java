package com.shareit.shareit.like.domain.entity;

import com.shareit.shareit.domain.BaseEntity;
import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.post.domain.entity.Post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
	@UniqueConstraint(
		name = "like_unique",
		columnNames = {"post_id", "member_id"}
	)
})
public class Likes extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

}
