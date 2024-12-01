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

	public Member getCurrentMember() {
		return memberPersistOutPort.findByAccountId(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	public void createMember(Member creatingMember) {
		memberPersistOutPort.save(creatingMember);
	}

	public Member getMemberByAccountId(String accountId) {
		return memberPersistOutPort.findByAccountId(accountId);
	}

	public boolean isExistsMemberByAccountId(String accountId) {
		return memberPersistOutPort.isExistsByAccountId(accountId);
	}
}
