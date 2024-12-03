package com.example.sktestpost.infra.adapter.out;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.sktestpost.application.port.out.PostFilePersistOutPort;
import com.example.sktestpost.domain.PostFile;
import com.example.sktestpost.infra.adapter.out.jpa.PostFileJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostFilePersistAdapter implements PostFilePersistOutPort {

	private final PostFileJpaRepository postFileJpaRepository;

	@Override
	public boolean isExistPostFile(Long postId) {
		return postFileJpaRepository.existsByPostId(postId);
	}

	@Override
	public void save(PostFile savingPostFile) {
		postFileJpaRepository.save(savingPostFile);
	}

	@Override
	public PostFile findByPostId(Long postId) {
		return postFileJpaRepository.findByPostId(postId);
	}

	@Override
	public List<PostFile> findAllByPostId(Long postId) {
		return postFileJpaRepository.findAllByPostId(postId);
	}
}
