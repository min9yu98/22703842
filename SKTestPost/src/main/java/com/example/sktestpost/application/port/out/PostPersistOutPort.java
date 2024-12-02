package com.example.sktestpost.application.port.out;

import com.example.sktestpost.domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostPersistOutPort {
	Post save(Post savingPost);

	Post findById(Long postId);

	Page<Post> findAll(Pageable pageable);

	Page<Post> findAllByKeyword(Pageable pageable, String keyword);
}