package com.shareit.shareit.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shareit.shareit.post.domain.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {

	@EntityGraph(attributePaths = {"member"})
	Optional<Post> findPostById(Long id);

	@Query("SELECT p FROM Post p ORDER BY p.updatedAt DESC")
	List<Post> findRecentTop3Post();
}
