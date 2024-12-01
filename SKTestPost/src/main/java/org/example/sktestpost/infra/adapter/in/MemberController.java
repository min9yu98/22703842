package org.example.sktestpost.infra.adapter.in;

import static org.example.sktestpost.common.constants.JwtConstants.*;

import org.example.sktestpost.application.port.in.MemberUseCase;
import org.example.sktestpost.common.dto.request.JoinReqDTO;
import org.example.sktestpost.common.dto.request.LoginReqDTO;
import org.example.sktestpost.common.dto.response.AfterJoinResDTO;
import org.example.sktestpost.common.dto.response.AfterLoginResDTO;
import org.example.sktestpost.common.dto.response.JoinResDTO;
import org.example.sktestpost.common.dto.response.LoginResDTO;
import org.example.sktestpost.common.response.ResultResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {

	private final MemberUseCase memberUseCase;

	@Operation(summary = "로그인")
	@PostMapping("/login")
	public ResponseEntity<ResultResponse> login(@RequestBody LoginReqDTO loginReqDTO) {
		log.info("login");
		LoginResDTO loginResDTO = memberUseCase.login(loginReqDTO);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(ACCESS_TOKEN.getContent(), loginResDTO.getAccessToken());
		httpHeaders.add(REFRESH_TOKEN.getContent(), loginResDTO.getRefreshToken());
		AfterLoginResDTO afterLoginResDTO = AfterLoginResDTO.builder().message("로그인 성공").build();
		return ResponseEntity.ok().headers(httpHeaders).body(new ResultResponse(afterLoginResDTO));
	}

	@Operation(summary = "회원가입")
	@PostMapping("/join")
	public ResponseEntity<ResultResponse> join(@RequestBody JoinReqDTO joinReqDTO) {
		log.info("join");
		JoinResDTO joinResDTO = memberUseCase.join(joinReqDTO);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(ACCESS_TOKEN.getContent(), joinResDTO.getAccessToken());
		httpHeaders.add(REFRESH_TOKEN.getContent(), joinResDTO.getRefreshToken());
		AfterJoinResDTO afterJoinResDTO = AfterJoinResDTO.builder().message("회원가입 성공").build();
		return ResponseEntity.ok().headers(httpHeaders).body(new ResultResponse(afterJoinResDTO));
	}

}
