package com.example.sktestpost.application.port.in;

import org.springframework.data.domain.Pageable;

import com.example.sktestpost.common.dto.request.CreatePostReqDTO;
import com.example.sktestpost.common.dto.request.UpdatePostReqDTO;
import com.example.sktestpost.common.dto.response.CreatePostResDTO;
import com.example.sktestpost.common.dto.response.DeletePostResDTO;
import com.example.sktestpost.common.dto.response.GetPostListResDTO;
import com.example.sktestpost.common.dto.response.GetPostResDTO;
import com.example.sktestpost.common.dto.response.UpdatePostResDTO;

public interface PostUseCase {
	CreatePostResDTO createPost(CreatePostReqDTO createPostReqDTO);

	UpdatePostResDTO updatePost(UpdatePostReqDTO updatePostReqDTO);

	DeletePostResDTO deletePost(Long postId);

	GetPostResDTO getPost(Long postId);

	GetPostListResDTO getPostList(Pageable pageable, String keyword, String category);
}
