package com.example.sktestpost.application.facade;

import static com.example.sktestpost.common.constants.JwtConstants.*;

import com.example.sktestpost.application.port.in.MemberUseCase;
import com.example.sktestpost.application.service.MemberService;
import com.example.sktestpost.common.dto.request.JoinReqDTO;
import com.example.sktestpost.common.dto.request.LoginReqDTO;
import com.example.sktestpost.common.dto.response.JoinResDTO;
import com.example.sktestpost.common.dto.response.LoginResDTO;
import com.example.sktestpost.common.dto.response.TokenResDTO;
import com.example.sktestpost.common.response.error.ErrorCode;
import com.example.sktestpost.common.response.exception.IllegalAccessException;
import com.example.sktestpost.common.utils.JwtUtils;
import com.example.sktestpost.domain.Member;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberFacade implements MemberUseCase {

	private final MemberService memberService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JwtUtils jwtUtils;

	@Override
	public LoginResDTO login(LoginReqDTO loginReqDTO) {
		Member member = memberService.getMemberByAccountId(loginReqDTO.getAccountId());
		if (!bCryptPasswordEncoder.matches(loginReqDTO.getAccountPwd(), member.getAccountPwd())) {
			throw new IllegalAccessException("비밀번호가 일치하지 않습니다.", ErrorCode.IllegalAccess);
		}
		TokenResDTO tokenResDTO = generateTokens(member);
		return LoginResDTO.builder()
			.accessToken(tokenResDTO.getAccessToken())
			.refreshToken(tokenResDTO.getRefreshToken())
			.build();
	}

	@Override
	public JoinResDTO join(JoinReqDTO joinReqDTO) {
		if (memberService.isExistsMemberByAccountId(joinReqDTO.getAccountId())) {
			throw new IllegalAccessException("이미 존재하는 아이디입니다.", ErrorCode.IllegalAccess);
		}
		Member creatingMember = Member.builder()
			.name(joinReqDTO.getName())
			.accountId(joinReqDTO.getAccountId())
			.accountPwd(bCryptPasswordEncoder.encode(joinReqDTO.getAccountPwd()))
			.role(ROLE_USER.getContent())
			.build();
		memberService.createMember(creatingMember);
		TokenResDTO tokenResDTO = generateTokens(creatingMember);
		return JoinResDTO.builder()
			.accessToken(tokenResDTO.getAccessToken())
			.refreshToken(tokenResDTO.getRefreshToken())
			.build();
	}

	private TokenResDTO generateTokens(Member member) {
		String accessToken = jwtUtils.generateAccessToken(member.getAccountId(), member.getRole());
		String refreshToken = jwtUtils.generateRefreshToken(member.getAccountId(), member.getRole());
		return TokenResDTO.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
