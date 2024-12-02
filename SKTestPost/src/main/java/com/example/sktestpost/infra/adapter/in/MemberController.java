package com.example.sktestpost.infra.adapter.in;

import static com.example.sktestpost.common.constants.JwtConstants.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sktestpost.application.port.in.MemberUseCase;
import com.example.sktestpost.common.dto.request.JoinReqDTO;
import com.example.sktestpost.common.dto.request.LoginReqDTO;
import com.example.sktestpost.common.dto.response.AfterJoinResDTO;
import com.example.sktestpost.common.dto.response.AfterLoginResDTO;
import com.example.sktestpost.common.dto.response.JoinResDTO;
import com.example.sktestpost.common.dto.response.LoginResDTO;
import com.example.sktestpost.common.response.ResultResponse;

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

	@Operation(summary = "로그아웃")
	@PostMapping("/logout")
	public ResponseEntity<ResultResponse> logout() {
		log.info("logout");
		return ResponseEntity.ok().body(new ResultResponse("로그아웃 성공"));
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
