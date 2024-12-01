package com.example.sktestpost.common.dto.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePostResDTO {

	private Long postId;
	private String title;
	private String writerAccountId;
	private LocalDate createAt;
	private String content;
	private Long postViewCount;

}
