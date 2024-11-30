package org.example.sktestpost.application.port.in;

import org.example.sktestpost.common.dto.request.CreatePostReqDTO;
import org.example.sktestpost.common.dto.request.DeletePostReqDTO;
import org.example.sktestpost.common.dto.request.UpdatePostReqDTO;
import org.example.sktestpost.common.dto.response.CreatePostResDTO;
import org.example.sktestpost.common.dto.response.DeletePostResDTO;
import org.example.sktestpost.common.dto.response.GetPostListResDTO;
import org.example.sktestpost.common.dto.response.GetPostResDTO;
import org.example.sktestpost.common.dto.response.UpdatePostResDTO;
import org.springframework.data.domain.Pageable;

public interface PostUseCase {
	CreatePostResDTO createPost(CreatePostReqDTO createPostReqDTO);

	UpdatePostResDTO updatePost(UpdatePostReqDTO updatePostReqDTO);

	DeletePostResDTO deletePost(DeletePostReqDTO deletePostReqDTO);

	GetPostResDTO getPost(Long postId);

	GetPostListResDTO getPostList(Pageable pageable);
}
