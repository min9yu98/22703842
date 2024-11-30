package org.example.sktestpost.application.facade;

import static org.assertj.core.api.Assertions.*;

import org.example.sktestpost.common.dto.request.CreatePostReqDTO;
import org.example.sktestpost.common.dto.request.DeletePostReqDTO;
import org.example.sktestpost.common.dto.request.UpdatePostReqDTO;
import org.example.sktestpost.common.dto.response.CreatePostResDTO;
import org.example.sktestpost.common.dto.response.UpdatePostResDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
		Long writerId = 1L;
		CreatePostReqDTO createPostReqDTO = new CreatePostReqDTO();
		createPostReqDTO.setTitle(title);
		createPostReqDTO.setContent(content);
		createPostReqDTO.setWriterId(writerId);

		// when
		CreatePostResDTO result = postFacade.createPost(createPostReqDTO);

		// then
		assertThat(result.getTitle()).isEqualTo(title);
		assertThat(result.getContent()).isEqualTo(content);
		assertThat(result.getWriterId()).isEqualTo(writerId);
	}

	@Test
	@DisplayName("포스트 생성 후 수정 성공 테스트")
	void updatePostTest() {
		// given
		CreatePostResDTO createdPost = createPost();
		Long postId = createdPost.getPostId();
		String title = "수정된 제목";
		String content = "수정된 내용";
		Long writerId = 1L;
		UpdatePostReqDTO updatePostReqDTO = new UpdatePostReqDTO();
		updatePostReqDTO.setTitle(title);
		updatePostReqDTO.setContent(content);
		updatePostReqDTO.setPostId(postId);

		// when
		UpdatePostResDTO result = postFacade.updatePost(updatePostReqDTO);

		// then
		assertThat(result.getTitle()).isNotEqualTo(createdPost.getTitle());
		assertThat(result.getContent()).isNotEqualTo(createdPost.getContent());
		assertThat(result.getWriterId()).isEqualTo(writerId);
	}

	@Test
	@DisplayName("포스트 생성 후 삭제 성공 테스트")
	void deletePost() {
		// given
		CreatePostResDTO createdPost = createPost();
		Long postId = createdPost.getPostId();
		Long writerId = createdPost.getWriterId();
		DeletePostReqDTO deletePostReqDTO = new DeletePostReqDTO();
		deletePostReqDTO.setPostId(postId);
		deletePostReqDTO.setWriterId(writerId);

		// when
		postFacade.deletePost(deletePostReqDTO);

		// then
		// assertThatException().isThrownBy(() -> postFacade.getPost(postId));
	}

	private CreatePostResDTO createPost() {
		String title = "테스트 제목";
		String content = "테스트 내용";
		Long writerId = 1L;
		CreatePostReqDTO createPostReqDTO = new CreatePostReqDTO();
		createPostReqDTO.setTitle(title);
		createPostReqDTO.setContent(content);
		createPostReqDTO.setWriterId(writerId);

		return postFacade.createPost(createPostReqDTO);
	}

}