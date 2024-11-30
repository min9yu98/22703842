package org.example.sktestpost.infra.adapter.out;

import org.example.sktestpost.application.port.out.MemberPersistOutPort;
import org.example.sktestpost.common.domain.Member;
import org.example.sktestpost.infra.adapter.out.jpa.MemberJpaRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberPersistAdapter implements MemberPersistOutPort {

	private final MemberJpaRepository memberJpaRepository;

	@Override
	public Member save(Member creatingMember) {
		return memberJpaRepository.save(creatingMember);
	}
}
