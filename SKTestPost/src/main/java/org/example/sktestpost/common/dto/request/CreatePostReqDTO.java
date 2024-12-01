package org.example.sktestpost.common.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostReqDTO {

	private String title;
	private String content;
	private String writerAccountId; // spring security 적용 후 수정 예정

}
