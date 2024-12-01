package org.example.sktestpost.application.facade;

import org.example.sktestpost.application.port.in.MemberUseCase;
import org.example.sktestpost.application.service.MemberService;
import org.example.sktestpost.common.dto.request.JoinReqDTO;
import org.example.sktestpost.common.dto.request.LoginReqDTO;
import org.example.sktestpost.common.dto.response.JoinResDTO;
import org.example.sktestpost.common.dto.response.LoginResDTO;
import org.example.sktestpost.common.response.error.ErrorCode;
import org.example.sktestpost.common.response.exception.IllegalAccessException;
import org.example.sktestpost.common.utils.JwtUtils;
import org.example.sktestpost.domain.Member;
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
		String accessToken = jwtUtils.generateAccessToken(member.getAccountId(), member.getRole());
		String refreshToken = jwtUtils.generateRefreshToken(member.getAccountId(), member.getRole());
		return LoginResDTO.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
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
			.role("USER")
			.build();
		memberService.createMember(creatingMember);
		String accessToken = jwtUtils.generateAccessToken(creatingMember.getAccountId(), creatingMember.getRole());
		String refreshToken = jwtUtils.generateRefreshToken(creatingMember.getAccountId(), creatingMember.getRole());
		return JoinResDTO.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
