package org.example.sktestpost.infra.adapter.out;

import org.example.sktestpost.application.port.out.PostPersistOutPort;
import org.example.sktestpost.common.domain.Post;
import org.example.sktestpost.infra.adapter.out.jpa.PostJpaRepository;
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
}
