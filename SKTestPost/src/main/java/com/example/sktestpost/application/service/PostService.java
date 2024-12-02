package com.example.sktestpost.application.service;

import static com.example.sktestpost.common.constants.Constants.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.sktestpost.application.port.out.PostPersistOutPort;
import com.example.sktestpost.common.dto.request.CreatePostReqDTO;
import com.example.sktestpost.domain.Member;
import com.example.sktestpost.domain.Post;

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

	public Post getPost(Long postId) {
		return postPersistOutPort.findById(postId);

	}

	public Page<Post> getPostList(Pageable pageable, String keyword, String category) {
		return switch (category) {
			case TITLE -> postPersistOutPort.findAllByTitle(pageable, keyword);
			case MEMBER_ACCOUNT_ID -> postPersistOutPort.findAllByMemberAccountId(pageable, keyword);
			default -> postPersistOutPort.findAllByKeyword(pageable, keyword);
		};
	}

}
