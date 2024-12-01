package com.example.sktestpost.infra.adapter.out;

import com.example.sktestpost.application.port.out.PostFilePersistOutPort;
import com.example.sktestpost.infra.adapter.out.jpa.PostFileJpaRepository;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostFilePersistAdapter implements PostFilePersistOutPort {

	private final PostFileJpaRepository postFileJpaRepository;

	@Override
	public boolean isExistPostFile(Long postId) {
		return postFileJpaRepository.existsById(postId);
	}
}
