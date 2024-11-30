package org.example.sktestpost.application.port.in;

import org.example.sktestpost.common.dto.request.CreatePostReqDTO;
import org.example.sktestpost.common.dto.response.CreatePostResDTO;

public interface PostUseCase {
	CreatePostResDTO createPost(CreatePostReqDTO createPostReqDTO);
}
