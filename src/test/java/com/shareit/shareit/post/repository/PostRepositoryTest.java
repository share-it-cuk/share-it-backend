package com.shareit.shareit.post.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.shareit.shareit.post.domain.entity.Post;
import com.shareit.shareit.post.domain.entity.PostImage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {

	@Test
	void test(){
		Post post = Post.builder()
			.title("test")
			.build();
		PostImage postImage = new PostImage();
		post.getPostImages().add(postImage);
		log.info("test={}", post.getPostImages().contains(postImage));
	}

}
