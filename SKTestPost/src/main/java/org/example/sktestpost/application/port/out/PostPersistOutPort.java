package org.example.sktestpost.application.port.out;

import org.example.sktestpost.domain.Post;

public interface PostPersistOutPort {
	Post save(Post savingPost);
}
