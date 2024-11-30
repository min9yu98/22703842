package org.example.sktestpost.application.service;

import org.example.sktestpost.application.port.out.PostPersistOutPort;
import org.example.sktestpost.common.dto.request.CreatePostReqDTO;
import org.example.sktestpost.common.dto.request.UpdatePostReqDTO;
import org.example.sktestpost.domain.Member;
import org.example.sktestpost.domain.Post;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostPersistOutPort postPersistOutPort;

	public Post createPost(CreatePostReqDTO createPostReqDTO, Member writer) {
		Post savingPost = Post.builder()
			.title(createPostReqDTO.getTitle())
			.content(createPostReqDTO.getContent())
			.member(writer)
			.build();
		return postPersistOutPort.save(savingPost);
	}

	public Post updatePost(UpdatePostReqDTO updatePostReqDTO) {
		Post post = postPersistOutPort.findById(updatePostReqDTO.getPostId());
		post.updatePost(updatePostReqDTO.getTitle(), updatePostReqDTO.getContent());
		return postPersistOutPort.save(post);
	}
}
