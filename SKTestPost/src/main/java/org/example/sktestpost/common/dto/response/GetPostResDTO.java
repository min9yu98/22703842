package org.example.sktestpost.common.dto.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetPostResDTO {

	private Long postId;
	private String title;
	private Long writerId;
	private LocalDate createdAt;
	private Long viewCount;
	private String content;

}
