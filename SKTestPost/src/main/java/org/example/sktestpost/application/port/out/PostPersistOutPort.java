package org.example.sktestpost.application.port.out;

import org.example.sktestpost.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostPersistOutPort {
	Post save(Post savingPost);

	Post findById(Long postId);

	Page<Post> findAll(Pageable pageable);
}
