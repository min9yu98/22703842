package org.example.sktestpost.application.facade;

import static org.assertj.core.api.Assertions.*;
import static org.example.sktestpost.common.constants.Constants.*;

import org.example.sktestpost.common.dto.request.CreatePostReqDTO;
import org.example.sktestpost.common.dto.request.DeletePostReqDTO;
import org.example.sktestpost.common.dto.request.UpdatePostReqDTO;
import org.example.sktestpost.common.dto.response.CreatePostResDTO;
import org.example.sktestpost.common.dto.response.GetPostListResDTO;
import org.example.sktestpost.common.dto.response.GetPostResDTO;
import org.example.sktestpost.common.dto.response.GetSearchPostListResDTO;
import org.example.sktestpost.common.dto.response.UpdatePostResDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("post facade test")
class PostFacadeTest {

	@Autowired
	private PostFacade postFacade;

	@Test
	@DisplayName("포스트 생성 성공 테스트")
	void createPostTest() {
		// given
		String title = "테스트 제목";
		String content = "테스트 내용";
		String writerId = "test";
		CreatePostReqDTO createPostReqDTO = new CreatePostReqDTO();
		createPostReqDTO.setTitle(title);
		createPostReqDTO.setContent(content);
		createPostReqDTO.setWriterAccountId(writerId);

		// when
		CreatePostResDTO result = postFacade.createPost(createPostReqDTO);

		// then
		assertThat(result.getTitle()).isEqualTo(title);
		assertThat(result.getContent()).isEqualTo(content);
		assertThat(result.getWriterAccountId()).isEqualTo(writerId);
	}

	@Test
	@DisplayName("포스트 생성 후 수정 성공 테스트")
	void updatePostTest() {
		// given
		CreatePostResDTO createdPost = createPost();
		Long postId = createdPost.getPostId();
		String title = "수정된 제목";
		String content = "수정된 내용";
		String writerId = "test";
		UpdatePostReqDTO updatePostReqDTO = new UpdatePostReqDTO();
		updatePostReqDTO.setTitle(title);
		updatePostReqDTO.setContent(content);
		updatePostReqDTO.setPostId(postId);

		// when
		UpdatePostResDTO result = postFacade.updatePost(updatePostReqDTO);

		// then
		assertThat(result.getTitle()).isNotEqualTo(createdPost.getTitle());
		assertThat(result.getContent()).isNotEqualTo(createdPost.getContent());
		assertThat(result.getWriterAccountId()).isEqualTo(writerId);
	}

	@Test
	@DisplayName("포스트 생성 후 삭제 성공 테스트")
	void deletePost() {
		// given
		CreatePostResDTO createdPost = createPost();
		Long postId = createdPost.getPostId();
		String writerAccountId = createdPost.getWriterAccountId();
		DeletePostReqDTO deletePostReqDTO = new DeletePostReqDTO();
		deletePostReqDTO.setPostId(postId);
		deletePostReqDTO.setWriterAccountId(writerAccountId);

		// when
		postFacade.deletePost(deletePostReqDTO);

		// then
		assertThatException().isThrownBy(() -> postFacade.getPost(postId));
	}

	@Test
	@DisplayName("포스트 생성 후 상세 조회 성공 테스트")
	void getPost() {
		// given
		CreatePostResDTO createdPost = createPost();
		Long postId = createdPost.getPostId();

		// when
		GetPostResDTO result1 = postFacade.getPost(postId);
		GetPostResDTO result2 = postFacade.getPost(postId);
		GetPostResDTO result3 = postFacade.getPost(postId);
		GetPostResDTO result = postFacade.getPost(postId);

		// then
		assertThat(result.getPostId()).isEqualTo(postId);
		assertThat(result.getTitle()).isEqualTo(createdPost.getTitle());
		assertThat(result.getContent()).isEqualTo(createdPost.getContent());
		assertThat(result.getCreatedAt()).isEqualTo(createdPost.getCreateAt());
		assertThat(result.getViewCount()).isEqualTo(4);
	}

	@Test
	@DisplayName("포스트 생성 후 목록 조회")
	void getPostList() {
		// given
		CreatePostResDTO createdPost = createPost();

		// when
		Pageable pageable = generatePageable(0);
		GetPostListResDTO result = postFacade.getPostList(pageable);

		// then
		assertThat(result.getPostList().size()).isEqualTo(1);
	}

	@Test
	@DisplayName("포스트 생성 후 검색 조회")
	void getSearchPostList() {
		// given
		CreatePostResDTO createdPost = createPost();
		String keyword = "테";
		String keyword2 = "케";

		// when
		Pageable pageable = generatePageable(0);
		GetSearchPostListResDTO result = postFacade.getSearchPostList(pageable, keyword);
		GetSearchPostListResDTO result2 = postFacade.getSearchPostList(pageable, keyword2);

		// then
		assertThat(result.getPostList().size()).isEqualTo(1);
		assertThat(result2.getPostList().size()).isEqualTo(0);
	}

	private CreatePostResDTO createPost() {
		String title = "테스트 제목";
		String content = "테스트 내용";
		String writerId = "test";
		CreatePostReqDTO createPostReqDTO = new CreatePostReqDTO();
		createPostReqDTO.setTitle(title);
		createPostReqDTO.setContent(content);
		createPostReqDTO.setWriterAccountId(writerId);

		return postFacade.createPost(createPostReqDTO);
	}

	private Pageable generatePageable(int page) {
		return PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, PAGE_SORT_CRITERIA));
	}

}