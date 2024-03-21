package com.shareit.shareit.purchase.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.shareit.shareit.domain.BaseEntity;
import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.post.domain.entity.Post;
import com.shareit.shareit.purchase.domain.util.PurchaseStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("active_status <> 'DELETED' and active_status <> PENDING")
@SQLDelete(sql = "UPDATE purchase SET active_status = 'DELETED' where purchase_id = ?")
public class Purchase extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchase_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne
	@JoinColumn(name = "visitor_id")
	private Member member;

	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String account;
	private Long cost;
	private PurchaseStatus purchaseStatus;

}
