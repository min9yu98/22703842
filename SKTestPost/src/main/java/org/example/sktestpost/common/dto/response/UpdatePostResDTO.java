package org.example.sktestpost.common.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdatePostResDTO {

	private Long postId;
	private String title;
	private Long writerId;
	private LocalDateTime createAt;
	private String content;
	private Long postViewCount;

}
