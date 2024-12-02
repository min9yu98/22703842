package com.example.sktestpost.application.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.sktestpost.domain.Post;

public interface PostPersistOutPort {
	Post save(Post savingPost);

	Post findById(Long postId);

	Page<Post> findAllByKeyword(Pageable pageable, String keyword);

	Page<Post> findAllByTitle(Pageable pageable, String keyword);

	Page<Post> findAllByMemberAccountId(Pageable pageable, String keyword);
}
