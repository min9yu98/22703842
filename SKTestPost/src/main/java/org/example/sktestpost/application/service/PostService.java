package org.example.sktestpost.application.service;

import org.example.sktestpost.application.port.out.PostPersistOutPort;
import org.example.sktestpost.common.domain.Member;
import org.example.sktestpost.common.domain.Post;
import org.example.sktestpost.common.dto.request.CreatePostReqDTO;
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
}
