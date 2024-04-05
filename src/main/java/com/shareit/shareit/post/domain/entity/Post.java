package com.shareit.shareit.post.domain.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.shareit.shareit.domain.BaseEntity;
import com.shareit.shareit.domain.entity.Campus;
import com.shareit.shareit.like.domain.entity.Likes;
import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.post.domain.PostType;
import com.shareit.shareit.purchase.domain.entity.Purchase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("active_status <> 'DELETED' and active_status <> 'PENDING'")
@SQLDelete(sql = "UPDATE post SET active_status = 'DELETED' where post_id = ?")
public class Post extends BaseEntity {

	@Id
	@Column(name = "post_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "campus_id")
	private Campus campus;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	private String hashTag;

	private String content;

	private String title;

	private Long cost;

	private PostType postType;

	private Integer perDate;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<PostImage> postImages;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Likes> likes;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Purchase> purchases;

	@Builder
	public Post(Campus campus, Member member, String hashTag, String content, String title,
		Long cost, PostType postType, Integer perDate) {
		this.campus = campus;
		this.member = member;
		this.hashTag = hashTag;
		this.content = content;
		this.title = title;
		this.cost = cost;
		this.postType = postType;
		this.perDate = perDate;
		this.postImages = new HashSet<>();
		this.likes = new HashSet<>();
		this.purchases = new HashSet<>();
	}

	// 연관 관계 편의 메서드
	public void addPostImage(PostImage postImage) {
		this.getPostImages().add(postImage);
		if (postImage.getPost() != this) {
			postImage.addPost(this);
		}
	}

}
