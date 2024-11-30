package org.example.sktestpost.application.service;

import org.example.sktestpost.application.port.out.PostPersistOutPort;
import org.example.sktestpost.common.dto.request.CreatePostReqDTO;
import org.example.sktestpost.common.dto.request.DeletePostReqDTO;
import org.example.sktestpost.domain.Member;
import org.example.sktestpost.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Post getPost(Long postId) {
		return postPersistOutPort.findById(postId);

	}

	public Long deletePost(DeletePostReqDTO deletePostReqDTO) {
		Post deletingPost = postPersistOutPort.findById(deletePostReqDTO.getPostId());
		Long deletedPostId = deletingPost.getId();
		deletingPost.delete();
		return deletedPostId;
	}

	public Page<Post> getPostList(Pageable pageable) {
		return postPersistOutPort.findAll(pageable);
	}

	public Page<Post> getSearchPostList(Pageable pageable, String keyword) {
		return postPersistOutPort.findAllByKeyword(pageable, keyword);
	}
}
