package org.example.sktestpost.application.service;

import org.example.sktestpost.application.port.out.MemberPersistOutPort;
import org.example.sktestpost.domain.Member;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberPersistOutPort memberPersistOutPort;

	// 임시 구현
	public Member getCurrentMemberForTest() {
		return createMember(Member.builder().name("test").accountId("test@gmail.com").build());
	}

	public Member getCurrentMember() {
		return memberPersistOutPort.findByAccountId(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	public Member createMember(Member creatingMember) {
		return memberPersistOutPort.save(creatingMember);
	}

	public Member getMemberByAccountId(String accountId) {
		return memberPersistOutPort.findByAccountId(accountId);
	}

	public boolean isExistsMemberByAccountId(String accountId) {
		return memberPersistOutPort.isExistsByAccountId(accountId);
	}
}
