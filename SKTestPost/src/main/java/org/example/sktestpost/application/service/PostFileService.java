package org.example.sktestpost.application.service;

import org.example.sktestpost.application.port.out.PostFilePersistOutPort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostFileService {

	private final PostFilePersistOutPort postFilePersistOutPort;

	public boolean isExistPostFile(Long postId) {
		return postFilePersistOutPort.isExistPostFile(postId);
	}
}
