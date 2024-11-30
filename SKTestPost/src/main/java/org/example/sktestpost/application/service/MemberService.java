package org.example.sktestpost.application.service;

import org.example.sktestpost.application.port.out.MemberPersistOutPort;
import org.example.sktestpost.domain.Member;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberPersistOutPort memberPersistOutPort;

	// 임시 구현
	public Member getCurrentMember() {
		return createMember(Member.builder().name("test").email("test@gmail.com").build());
	}

	public Member createMember(Member creatingMember) {
		return memberPersistOutPort.save(creatingMember);
	}

}
