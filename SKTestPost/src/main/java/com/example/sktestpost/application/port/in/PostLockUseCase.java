package com.example.sktestpost.application.port.in;

import com.example.sktestpost.common.dto.response.GetPostResDTO;

public interface PostLockUseCase {

	GetPostResDTO getPost(Long postId);

}
