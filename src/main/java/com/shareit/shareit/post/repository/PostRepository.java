package com.shareit.shareit.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shareit.shareit.post.domain.PostType;
import com.shareit.shareit.post.domain.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	@EntityGraph(attributePaths = {"postImages"})
	@Query("select p from Post p where p.postType=:type")
	List<Post> findAllWithImages(@Param("type") PostType postType);

}
