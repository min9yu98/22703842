package com.example.sktestpost.common.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeletePostReqDTO {

	private Long postId;
	private String writerAccountId;

}
