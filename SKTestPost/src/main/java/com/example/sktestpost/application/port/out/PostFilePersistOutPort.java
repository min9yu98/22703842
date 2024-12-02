package com.example.sktestpost.application.port.out;

import com.example.sktestpost.domain.PostFile;

public interface PostFilePersistOutPort {
	boolean isExistPostFile(Long postId);

	void save(PostFile savingPostFile);

	PostFile findByPostId(Long postId);
}
