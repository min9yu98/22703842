package org.example.sktestpost.infra.adapter.in;

import org.example.sktestpost.application.port.in.PostUseCase;
import org.example.sktestpost.common.dto.request.CreatePostReqDTO;
import org.example.sktestpost.common.dto.request.UpdatePostReqDTO;
import org.example.sktestpost.common.dto.response.CreatePostResDTO;
import org.example.sktestpost.common.dto.response.UpdatePostResDTO;
import org.example.sktestpost.common.response.ResultResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Slf4j
public class PostController {

	private final PostUseCase postUseCase;

	@Operation(summary = "게시글 생성")
	@PostMapping
	public ResponseEntity<ResultResponse> createPost(@RequestBody CreatePostReqDTO createPostReqDTO) {
		log.info("create post");
		CreatePostResDTO createPostResDTO = postUseCase.createPost(createPostReqDTO);
		return ResponseEntity.ok(new ResultResponse(createPostResDTO));
	}

	@Operation(summary = "게시글 수정")
	@PatchMapping
	public ResponseEntity<ResultResponse> updatePost(@RequestBody UpdatePostReqDTO updatePostReqDTO) {
		log.info("update post");
		UpdatePostResDTO updatePostResDTO = postUseCase.updatePost(updatePostReqDTO);
		return ResponseEntity.ok(new ResultResponse(updatePostResDTO));
	}

}
