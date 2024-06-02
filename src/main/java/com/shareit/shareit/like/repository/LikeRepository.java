package com.shareit.shareit.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shareit.shareit.like.domain.entity.Likes;
import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.post.domain.entity.Post;

public interface LikeRepository extends JpaRepository<Likes, Long> {

	@EntityGraph(attributePaths = {"member", "post"})
	@Query("select l from Likes l where l.member=:member and l.post=:post")
	Optional<Likes> findLikesWithMemberPost(@Param("member")Member member, @Param("post")Post post);
}
