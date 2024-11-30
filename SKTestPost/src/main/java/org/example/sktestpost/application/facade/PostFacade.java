package org.example.sktestpost.application.facade;

import org.example.sktestpost.application.port.in.PostUseCase;
import org.example.sktestpost.application.service.MemberService;
import org.example.sktestpost.application.service.PostService;
import org.example.sktestpost.common.dto.request.CreatePostReqDTO;
import org.example.sktestpost.common.dto.response.CreatePostResDTO;
import org.example.sktestpost.domain.Member;
import org.example.sktestpost.domain.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostFacade implements PostUseCase {

	private final PostService postService;
	private final MemberService memberService;

	@Override
	@Transactional
	public CreatePostResDTO createPost(CreatePostReqDTO createPostReqDTO) {
		Member writer = memberService.getCurrentMember();
		Post createdPost = postService.createPost(createPostReqDTO, writer);
		return CreatePostResDTO.builder()
			.title(createdPost.getTitle())
			.content(createdPost.getContent())
			.createAt(createdPost.getCreatedAt())
			.postViewCount(createdPost.getViewCount())
			.writerId(writer.getId())
			.build();
	}

}
