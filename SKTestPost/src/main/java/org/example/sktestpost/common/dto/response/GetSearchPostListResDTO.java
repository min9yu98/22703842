package org.example.sktestpost.common.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetSearchPostListResDTO {

	private int pageNumber;
	private int pageCount;
	List<GetPostThumbNailResDTO> postList;

}
