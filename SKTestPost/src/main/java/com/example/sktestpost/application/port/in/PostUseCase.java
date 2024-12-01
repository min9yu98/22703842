package com.example.sktestpost.application.port.in;

import com.example.sktestpost.common.dto.request.CreatePostReqDTO;
import com.example.sktestpost.common.dto.request.DeletePostReqDTO;
import com.example.sktestpost.common.dto.request.UpdatePostReqDTO;
import com.example.sktestpost.common.dto.response.CreatePostResDTO;
import com.example.sktestpost.common.dto.response.DeletePostResDTO;
import com.example.sktestpost.common.dto.response.GetPostListResDTO;
import com.example.sktestpost.common.dto.response.GetPostResDTO;
import com.example.sktestpost.common.dto.response.GetSearchPostListResDTO;
import com.example.sktestpost.common.dto.response.UpdatePostResDTO;

import org.springframework.data.domain.Pageable;

public interface PostUseCase {
	CreatePostResDTO createPost(CreatePostReqDTO createPostReqDTO);

	UpdatePostResDTO updatePost(UpdatePostReqDTO updatePostReqDTO);

	DeletePostResDTO deletePost(DeletePostReqDTO deletePostReqDTO);

	GetPostResDTO getPost(Long postId);

	GetPostListResDTO getPostList(Pageable pageable);

	GetSearchPostListResDTO getSearchPostList(Pageable pageable, String keyword);
}
