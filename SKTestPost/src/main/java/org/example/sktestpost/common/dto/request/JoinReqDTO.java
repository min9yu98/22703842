package org.example.sktestpost.common.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinReqDTO {

	private String name;
	private String accountId;
	private String accountPwd;
	private String role;

}
