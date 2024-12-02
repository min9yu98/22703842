package com.example.sktestpost.application.port.in;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.sktestpost.common.dto.request.CreatePostReqDTO;
import com.example.sktestpost.common.dto.request.UpdatePostReqDTO;
import com.example.sktestpost.common.dto.response.CreatePostResDTO;
import com.example.sktestpost.common.dto.response.DeletePostResDTO;
import com.example.sktestpost.common.dto.response.GetPostListResDTO;
import com.example.sktestpost.common.dto.response.GetPostResDTO;
import com.example.sktestpost.common.dto.response.UpdatePostResDTO;
import com.example.sktestpost.common.dto.response.UploadPostFileResDTO;

public interface PostUseCase {
	CreatePostResDTO createPost(CreatePostReqDTO createPostReqDTO);

	UpdatePostResDTO updatePost(UpdatePostReqDTO updatePostReqDTO);

	DeletePostResDTO deletePost(Long postId);

	GetPostResDTO getPost(Long postId);

	GetPostListResDTO getPostList(Pageable pageable, String keyword, String category);

	UploadPostFileResDTO uploadPostFile(Long postId, MultipartFile file);

	void deletePostFile(Long postId, String fileUrl);
}
