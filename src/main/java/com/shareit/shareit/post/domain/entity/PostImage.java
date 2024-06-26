package com.shareit.shareit.post.domain.entity;


import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.shareit.shareit.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("active_status <> 'DELETED' and active_status <> 'PENDING'")
@SQLDelete(sql = "UPDATE post SET active_status = 'DELETED' where post_id = ?")
public class PostImage extends BaseEntity {

	@Id
	@Column(name = "post_image_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	private String imageKey;

	@Builder
	public PostImage(Post post, String imageKey) {
		this.post = post;
		this.imageKey = imageKey;

	}

	// 연관 관계 편의 메서드
	public void addPost(Post post) {
		if (this.post != post){
			this.post = post;
		}
		if (!post.getPostImages().contains(this)){
			post.addPostImage(this);
		}
	}

}
