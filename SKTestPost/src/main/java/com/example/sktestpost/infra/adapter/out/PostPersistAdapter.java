package com.example.sktestpost.infra.adapter.out;

import com.example.sktestpost.application.port.out.PostPersistOutPort;
import com.example.sktestpost.common.response.error.ErrorCode;
import com.example.sktestpost.common.response.exception.NotFoundException;
import com.example.sktestpost.domain.Post;
import com.example.sktestpost.infra.adapter.out.jpa.PostJpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostPersistAdapter implements PostPersistOutPort {

	private final PostJpaRepository postJpaRepository;

	@Override
	public Post save(Post savingPost) {
		return postJpaRepository.save(savingPost);
	}

	@Override
	public Post findById(Long postId) {
		return postJpaRepository.findById(postId)
			.orElseThrow(() -> new NotFoundException("존재하지 않는 게시글입니다.", ErrorCode.NOT_FOUND));
	}

	@Override
	public Page<Post> findAll(Pageable pageable) {
		return postJpaRepository.findAll(pageable);
	}

	@Override
	public Page<Post> findAllByKeyword(Pageable pageable, String keyword) {
		return postJpaRepository.findAllByKeyword(pageable, keyword);
	}
}
