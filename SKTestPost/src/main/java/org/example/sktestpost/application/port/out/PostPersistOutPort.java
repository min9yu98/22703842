package org.example.sktestpost.application.port.out;

import org.example.sktestpost.common.domain.Post;

public interface PostPersistOutPort {
	Post save(Post savingPost);
}
