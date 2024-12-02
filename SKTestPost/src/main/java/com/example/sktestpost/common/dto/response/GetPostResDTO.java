package com.example.sktestpost.common.dto.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetPostResDTO {

	private Long postId;
	private String title;
	private String writerAccountId;
	private LocalDate createdAt;
	private Long viewCount;
	private String content;
	private boolean isMine;

}
