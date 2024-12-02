package com.example.sktestpost.infra.adapter.in;

import static com.example.sktestpost.common.constants.Constants.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.sktestpost.application.port.in.PostLockUseCase;
import com.example.sktestpost.application.port.in.PostUseCase;
import com.example.sktestpost.common.dto.request.CreatePostReqDTO;
import com.example.sktestpost.common.dto.request.UpdatePostReqDTO;
import com.example.sktestpost.common.dto.response.CreatePostResDTO;
import com.example.sktestpost.common.dto.response.DeletePostResDTO;
import com.example.sktestpost.common.dto.response.GetPostListResDTO;
import com.example.sktestpost.common.dto.response.GetPostResDTO;
import com.example.sktestpost.common.dto.response.UpdatePostResDTO;
import com.example.sktestpost.common.dto.response.UploadPostFileResDTO;
import com.example.sktestpost.common.response.ResultResponse;

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
	private final PostLockUseCase postLockUseCase;

	@Operation(summary = "게시글 생성")
	@PostMapping
	public ResponseEntity<ResultResponse> createPost(@RequestBody CreatePostReqDTO createPostReqDTO) {
		log.info("create post");
		CreatePostResDTO createPostResDTO = postUseCase.createPost(createPostReqDTO);
		return ResponseEntity.ok(new ResultResponse(createPostResDTO));
	}

	@Operation(summary = "게시글 파일 업로드")
	@PostMapping(value = "/files/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResultResponse> uploadPostFile(
		@PathVariable(value = "postId") Long postId,
		@RequestPart("file") MultipartFile file) {
		UploadPostFileResDTO updatePostFileResDTO = postUseCase.uploadPostFile(postId, file);
		return ResponseEntity.ok(new ResultResponse(updatePostFileResDTO));
	}

	@Operation(summary = "업로드된 파일 삭제")
	@DeleteMapping(value = "/files/{postId}")
	public ResponseEntity<ResultResponse> uploadPostFile(
		@PathVariable(value = "postId") Long postId,
		@RequestParam(value = "fileUrl") String fileUrl) {
		postUseCase.deletePostFile(postId, fileUrl);
		return ResponseEntity.ok(new ResultResponse("파일 삭제 성공"));
	}

	@Operation(summary = "게시글 수정")
	@PatchMapping
	public ResponseEntity<ResultResponse> updatePost(@RequestBody UpdatePostReqDTO updatePostReqDTO) {
		log.info("update post");
		UpdatePostResDTO updatePostResDTO = postUseCase.updatePost(updatePostReqDTO);
		return ResponseEntity.ok(new ResultResponse(updatePostResDTO));
	}

	@Operation(summary = "게시글 삭제")
	@DeleteMapping("/{postId}")
	public ResponseEntity<ResultResponse> deletePost(@PathVariable("postId") Long postId) {
		log.info("delete post");
		DeletePostResDTO deletePostResDTO = postUseCase.deletePost(postId);
		return ResponseEntity.ok(new ResultResponse(deletePostResDTO));
	}

	@Operation(summary = "게시글 상세 조회")
	@GetMapping("/{postId}")
	public ResponseEntity<ResultResponse> getPost(@PathVariable("postId") Long postId) {
		log.info("get post");
		GetPostResDTO getPostResDTO = postLockUseCase.getPost(postId);
		return ResponseEntity.ok(new ResultResponse(getPostResDTO));
	}

	@Operation(summary = "게시글 목록 조회")
	@GetMapping
	public ResponseEntity<ResultResponse> getPostList(
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "keyword", required = false) String keyword,
		@RequestParam(value = "category", required = true) String category) {
		log.info("get post list");
		Pageable pageable = generatePageable(page);
		GetPostListResDTO getPostListResDTO = postUseCase.getPostList(pageable, keyword, category);
		return ResponseEntity.ok(new ResultResponse(getPostListResDTO));
	}

	private Pageable generatePageable(int page) {
		return PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, PAGE_SORT_CRITERIA));
	}
}
