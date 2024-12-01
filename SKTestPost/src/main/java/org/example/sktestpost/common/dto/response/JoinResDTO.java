package org.example.sktestpost.common.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinResDTO {

	private String accessToken;
	private String refreshToken;

}
