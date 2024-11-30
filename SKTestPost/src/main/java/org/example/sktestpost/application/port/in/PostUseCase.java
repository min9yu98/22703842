package org.example.sktestpost.application.port.in;

import org.example.sktestpost.common.dto.request.CreatePostReqDTO;
import org.example.sktestpost.common.dto.request.UpdatePostReqDTO;
import org.example.sktestpost.common.dto.response.CreatePostResDTO;
import org.example.sktestpost.common.dto.response.UpdatePostResDTO;

public interface PostUseCase {
	CreatePostResDTO createPost(CreatePostReqDTO createPostReqDTO);

	UpdatePostResDTO updatePost(UpdatePostReqDTO updatePostReqDTO);
}
