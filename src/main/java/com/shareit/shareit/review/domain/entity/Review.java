package com.shareit.shareit.review.domain.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.shareit.shareit.domain.BaseEntity;
import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.purchase.domain.entity.Purchase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("active_status <> 'DELETED' and active_status <> 'PENDING'")
@SQLDelete(sql = "UPDATE review SET active_status = 'DELETED' where review_id = ?")
public class Review extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name  = "review_id")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purhcase_id", referencedColumnName = "purchase_id")
	private Purchase purchase;

	@ManyToOne
	@JoinColumn(name = "reviewer_id")
	private Member reviewer;

	@ManyToOne
	@JoinColumn(name = "receiver_id")
	private Member receiver;

	private String content;
	private Integer score;

}
