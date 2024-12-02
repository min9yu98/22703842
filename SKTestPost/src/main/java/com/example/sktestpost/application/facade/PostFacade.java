package com.example.sktestpost.application.facade;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sktestpost.application.port.in.PostUseCase;
import com.example.sktestpost.application.service.MemberService;
import com.example.sktestpost.application.service.PostFileService;
import com.example.sktestpost.application.service.PostService;
import com.example.sktestpost.common.dto.request.CreatePostReqDTO;
import com.example.sktestpost.common.dto.request.UpdatePostReqDTO;
import com.example.sktestpost.common.dto.response.CreatePostResDTO;
import com.example.sktestpost.common.dto.response.DeletePostResDTO;
import com.example.sktestpost.common.dto.response.GetPostListResDTO;
import com.example.sktestpost.common.dto.response.GetPostResDTO;
import com.example.sktestpost.common.dto.response.GetPostThumbNailResDTO;
import com.example.sktestpost.common.dto.response.GetSearchPostListResDTO;
import com.example.sktestpost.common.dto.response.UpdatePostResDTO;
import com.example.sktestpost.common.response.error.ErrorCode;
import com.example.sktestpost.common.response.exception.IllegalAccessException;
import com.example.sktestpost.domain.Member;
import com.example.sktestpost.domain.Post;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostFacade implements PostUseCase {

	private final PostService postService;
	private final PostFileService postFileService;
	private final MemberService memberService;

	@Override
	public CreatePostResDTO createPost(CreatePostReqDTO createPostReqDTO) {
		Member currentMemberAsWriter = memberService.getCurrentMember();
		Post createdPost = postService.createPost(createPostReqDTO, currentMemberAsWriter);
		return CreatePostResDTO.builder()
			.postId(createdPost.getId())
			.title(createdPost.getTitle())
			.content(createdPost.getContent())
			.createAt(createdPost.getCreatedAt().toLocalDate())
			.postViewCount(createdPost.getViewCount())
			.writerAccountId(currentMemberAsWriter.getAccountId())
			.build();
	}

	@Override
	public UpdatePostResDTO updatePost(UpdatePostReqDTO updatePostReqDTO) {
		Member currentMember = memberService.getCurrentMember();
		Post updatingPost = postService.getPost(updatePostReqDTO.getPostId());
		updatingPost.updatePost(updatePostReqDTO.getTitle(), updatePostReqDTO.getContent());
		isPostWriter(updatingPost.getMember().getId());
		return UpdatePostResDTO.builder()
			.postId(updatingPost.getId())
			.title(updatingPost.getTitle())
			.content(updatingPost.getContent())
			.createAt(updatingPost.getCreatedAt().toLocalDate())
			.postViewCount(updatingPost.getViewCount())
			.writerAccountId(currentMember.getAccountId())
			.build();
	}

	@Override
	public DeletePostResDTO deletePost(Long postId) {
		Post deletingPost = postService.getPost(postId);
		deletingPost.delete();
		isPostWriter(deletingPost.getMember().getId());
		return DeletePostResDTO.builder()
			.postId(postId)
			.build();
	}

	// 게시글 작성인지 검증하는 메서드
	public void isPostWriter(Long writerId) {
		Member writer = memberService.getCurrentMember();
		if (!writer.getId().equals(writerId)) {
			throw new IllegalAccessException(ErrorCode.IllegalAccess);
		}
	}

	@Override
	public GetPostResDTO getPost(Long postId) {
		Post gettingPost = postService.getPost(postId);
		gettingPost.increaseViewCount();
		return GetPostResDTO.builder()
			.postId(postId)
			.title(gettingPost.getTitle())
			.writerAccountId(gettingPost.getMember().getAccountId())
			.createdAt(gettingPost.getCreatedAt().toLocalDate())
			.viewCount(gettingPost.getViewCount())
			.content(gettingPost.getContent())
			.isMine(gettingPost.getMember().getId().equals(memberService.getCurrentMember().getId()))
			.build();
	}

	@Override
	@Transactional(readOnly = true)
	public GetPostListResDTO getPostList(Pageable pageable) {
		Page<Post> postList = postService.getPostList(pageable);
		return GetPostListResDTO.builder()
			.pageCount(postList.getTotalPages())
			.pageNumber(pageable.getPageNumber())
			.postList(postList.stream().map(post -> GetPostThumbNailResDTO.builder()
					.postId(post.getId())
					.title(post.getTitle())
					.writerAccountId(post.getMember().getAccountId())
					.postViewCount(post.getViewCount())
					.postFileState(postFileService.isExistPostFile(post.getId()))
					.createdAt(post.getCreatedAt().toLocalDate())
					.build())
				.collect(Collectors.toList()))
			.build();
	}

	@Override
	@Transactional(readOnly = true)
	public GetSearchPostListResDTO getSearchPostList(Pageable pageable, String keyword) {
		Page<Post> searchPostList = postService.getSearchPostList(pageable, keyword);
		return GetSearchPostListResDTO.builder()
			.pageCount(searchPostList.getTotalPages())
			.pageNumber(pageable.getPageNumber())
			.postList(searchPostList.stream().map(post -> GetPostThumbNailResDTO.builder()
					.postId(post.getId())
					.title(post.getTitle())
					.writerAccountId(post.getMember().getAccountId())
					.postViewCount(post.getViewCount())
					.postFileState(postFileService.isExistPostFile(post.getId()))
					.createdAt(post.getCreatedAt().toLocalDate())
					.build())
				.collect(Collectors.toList()))
			.build();
	}
}
