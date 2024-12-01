package org.example.sktestpost.application.port.in;

import org.example.sktestpost.common.dto.request.JoinReqDTO;
import org.example.sktestpost.common.dto.request.LoginReqDTO;
import org.example.sktestpost.common.dto.response.JoinResDTO;
import org.example.sktestpost.common.dto.response.LoginResDTO;

public interface MemberUseCase {
	LoginResDTO login(LoginReqDTO loginReqDTO);

	JoinResDTO join(JoinReqDTO joinReqDTO);
}
