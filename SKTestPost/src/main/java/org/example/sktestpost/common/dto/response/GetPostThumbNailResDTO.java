package org.example.sktestpost.common.dto.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetPostThumbNailResDTO {

	private Long postId;
	private String title;
	private Long writerId;
	private Long postViewCount;
	private boolean postFileState;
	private LocalDate createdAt;

}
