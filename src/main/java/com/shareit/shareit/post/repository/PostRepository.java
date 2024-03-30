package com.shareit.shareit.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shareit.shareit.post.domain.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}