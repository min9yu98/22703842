package com.example.sktestpost.common.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetPostListResDTO {

	private int pageNumber;
	private int pageCount;
	List<GetPostThumbNailResDTO> postList;

}
