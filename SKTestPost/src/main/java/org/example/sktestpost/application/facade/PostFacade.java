package org.example.sktestpost.application.facade;

import org.example.sktestpost.application.port.in.PostUseCase;
import org.example.sktestpost.application.service.MemberService;
import org.example.sktestpost.application.service.PostService;
import org.example.sktestpost.common.dto.request.CreatePostReqDTO;
import org.example.sktestpost.common.dto.request.UpdatePostReqDTO;
import org.example.sktestpost.common.dto.response.CreatePostResDTO;
import org.example.sktestpost.common.dto.response.UpdatePostResDTO;
import org.example.sktestpost.common.response.error.ErrorCode;
import org.example.sktestpost.common.response.exception.IllegalAccessException;
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
		Member currentMemberAsWriter = memberService.getCurrentMemberForTest();
		Post createdPost = postService.createPost(createPostReqDTO, currentMemberAsWriter);
		return CreatePostResDTO.builder()
			.postId(createdPost.getId())
			.title(createdPost.getTitle())
			.content(createdPost.getContent())
			.createAt(createdPost.getCreatedAt())
			.postViewCount(createdPost.getViewCount())
			.writerId(currentMemberAsWriter.getId())
			.build();
	}

	@Override
	public UpdatePostResDTO updatePost(UpdatePostReqDTO updatePostReqDTO) {
		Member currentMember = memberService.getCurrentMember();
		Post updatedPost = postService.updatePost(updatePostReqDTO);
		isPostWriter(updatedPost.getMember().getId());
		return UpdatePostResDTO.builder()
			.postId(updatedPost.getId())
			.title(updatedPost.getTitle())
			.content(updatedPost.getContent())
			.createAt(updatedPost.getCreatedAt())
			.postViewCount(updatedPost.getViewCount())
			.writerId(currentMember.getId())
			.build();
	}

	// 게시글 작성인지 검증하는 메서드
	public void isPostWriter(Long writerId) {
		Member writer = memberService.getCurrentMember();
		if (!writer.getId().equals(writerId)) {
			throw new IllegalAccessException(ErrorCode.IllegalAccess);
		}
	}
}
