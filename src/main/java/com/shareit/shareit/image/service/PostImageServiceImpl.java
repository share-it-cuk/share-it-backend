package com.shareit.shareit.image.service;

import java.io.IOException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.shareit.shareit.Exceptions.BusinessException;
import com.shareit.shareit.domain.Response;
import com.shareit.shareit.domain.ResponseCode;
import com.shareit.shareit.image.dto.ImageUploadRequest;
import com.shareit.shareit.post.domain.entity.Post;
import com.shareit.shareit.post.domain.entity.PostImage;
import com.shareit.shareit.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostImageServiceImpl implements PostImageService {

	private final S3Client s3Client;
	private final PostRepository postRepository;
	@Value("${spring.aws.bucket-name}")
	private String bucketName;

	@Override
	public Response<Void> uploadPostImage(ImageUploadRequest request) {

		Long postId = request.getPostId();

		Post post = postRepository.findPostById(postId).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.POST_NOT_FOUND));

		for (MultipartFile file: request.getFiles()){

			String key = post.getId() + "/" + file.getOriginalFilename();

			PutObjectRequest fileRequest = PutObjectRequest.builder()
				.bucket(bucketName)
				.key(key)
				.build();

			byte[] fileBytes;

			try{
				 fileBytes = file.getBytes();

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			s3Client.putObject(fileRequest, RequestBody.fromBytes(fileBytes));

			URL url = s3Client.utilities().getUrl(b -> b.bucket(bucketName).key(key));

			PostImage postImage = PostImage.builder()
				.imageKey(url.toString())
				.build();

			post.addPostImage(postImage);
		}

		return Response.ok();
	}
}
