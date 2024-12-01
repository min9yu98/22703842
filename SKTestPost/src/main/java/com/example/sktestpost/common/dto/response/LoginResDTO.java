package com.example.sktestpost.common.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResDTO {

	private String accessToken;
	private String refreshToken;

}
