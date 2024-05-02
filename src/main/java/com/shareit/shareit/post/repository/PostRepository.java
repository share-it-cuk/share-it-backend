package com.shareit.shareit.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shareit.shareit.post.domain.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {

	@EntityGraph(attributePaths = {"member"})
	Optional<Post> findPostById(Long id);

}
