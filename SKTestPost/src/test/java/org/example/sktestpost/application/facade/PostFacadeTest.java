package org.example.sktestpost.application.facade;

import static org.assertj.core.api.Assertions.*;

import org.example.sktestpost.common.dto.request.CreatePostReqDTO;
import org.example.sktestpost.common.dto.response.CreatePostResDTO;
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

}