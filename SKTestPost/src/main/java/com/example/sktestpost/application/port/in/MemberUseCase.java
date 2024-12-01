package com.example.sktestpost.application.port.in;

import com.example.sktestpost.common.dto.request.JoinReqDTO;
import com.example.sktestpost.common.dto.request.LoginReqDTO;
import com.example.sktestpost.common.dto.response.JoinResDTO;
import com.example.sktestpost.common.dto.response.LoginResDTO;

public interface MemberUseCase {
	LoginResDTO login(LoginReqDTO loginReqDTO);

	JoinResDTO join(JoinReqDTO joinReqDTO);
}
