package com.shareit.shareit.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.shareit.shareit.domain.entity.Campus;
import com.shareit.shareit.member.domain.entity.Member;
import com.shareit.shareit.post.domain.PostType;
import com.shareit.shareit.post.domain.entity.Post;
import com.shareit.shareit.post.domain.entity.PostImage;
import com.shareit.shareit.post.repository.PostRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {

	private final String testString1 = "tes1";
	private final String testString2 = "test2";

	private final Long testCost1 = 0L;
	private final Long testCost2 = 1000L;

	private Campus testCampus;

	private Member member1;
	private Member member2;

	@Autowired
	private EntityManager em;
	@Autowired
	private PostRepository postRepository;

	@BeforeEach
	void beforeEach() {

		testCampus = Campus.builder()
			.campusName(testString1)
			.build();

		em.persist(testCampus);

		member1 = Member.builder()
			.email(testString1)
			.password(testString1)
			.nickname(testString1)
			.campus(testCampus)
			.build();

		member2 = Member.builder()
			.email(testString2)
			.password(testString2)
			.nickname(testString2)
			.campus(testCampus)
			.build();

		em.persist(member1);
		em.persist(member2);

	}

	@Test
	@DisplayName("post 엔티티 생성")
	void createPost() {
		//Given
		Post newPost = Post.builder()
			.title(testString1)
			.cost(testCost1)
			.campus(testCampus)
			.member(member1)
			.build();

		//When
		Post savePost = postRepository.save(newPost);

		//Then
		log.info("new post id = {}", newPost.getId());

		Post findPost = em.find(Post.class, newPost.getId());

		assertThat(savePost).isEqualTo(findPost);
	}

	@Test
	@DisplayName("post 엔티티 전체 찾아오기")
	void findPosts() {
		//Given
		Post post1 = Post.builder()
			.member(member1)
			.title(testString1)
			.content(testString1)
			.campus(testCampus)
			.cost(testCost1)
			.build();

		Post post2 = Post.builder()
			.member(member2)
			.title(testString2)
			.content(testString2)
			.campus(testCampus)
			.cost(testCost2)
			.build();

		em.persist(post1);
		em.persist(post2);

		//When
		List<Post> all = postRepository.findAll();

		//Then
		assertThat(all).hasSize(2);

	}

	@Test
	@DisplayName("post 엔티티들 이미지와 함께 찾아오기")
	void findPostsWithImages() {
		//Given
		Post post1 = Post.builder()
			.member(member1)
			.title(testString1)
			.content(testString1)
			.campus(testCampus)
			.cost(testCost1)
			.postType(PostType.NEED)
			.build();

		Post post2 = Post.builder()
			.member(member2)
			.title(testString2)
			.content(testString2)
			.campus(testCampus)
			.cost(testCost2)
			.postType(PostType.NEED)
			.build();

		em.persist(post1);
		em.persist(post2);

		PostImage postImage1 = PostImage.builder()
			.imageKey(testString1)
			.build();

		PostImage postImage2 = PostImage.builder()
			.imageKey(testString1)
			.build();

		PostImage postImage3 = PostImage.builder()
			.imageKey(testString2)
			.build();

		post1.addPostImage(postImage1);
		post1.addPostImage(postImage2);
		post2.addPostImage(postImage3);

		//When
		List<Post> findPosts = postRepository.findAllWithImages(PostType.NEED);

		//Then
		findPosts.forEach(post -> {
			assertThat(post.getPostImages()).isNotEmpty();
			log.info("post image size = {}", post.getPostImages().size());
		});

	}
}
