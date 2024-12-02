package com.example.sktestpost.application.service;

import org.springframework.stereotype.Service;

import com.example.sktestpost.application.port.out.PostFilePersistOutPort;
import com.example.sktestpost.domain.Post;
import com.example.sktestpost.domain.PostFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostFileService {

	private final PostFilePersistOutPort postFilePersistOutPort;

	public boolean isExistPostFile(Long postId) {
		return postFilePersistOutPort.isExistPostFile(postId);
	}

	public void savePostFile(Post post, String fileUrl) {
		PostFile savingPostFile = PostFile.builder()
			.post(post)
			.postFileUrl(fileUrl)
			.build();
		postFilePersistOutPort.save(savingPostFile);
	}

	public void deletePostFile(Long postId, String fileUrl) {
		PostFile deletingPostFile = postFilePersistOutPort.findByPostId(postId);
		deletingPostFile.delete();
	}
}
