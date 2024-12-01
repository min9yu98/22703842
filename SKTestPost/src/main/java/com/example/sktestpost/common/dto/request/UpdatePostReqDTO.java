package com.example.sktestpost.common.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePostReqDTO {

	private Long postId;
	private String title;
	private String content;

}
