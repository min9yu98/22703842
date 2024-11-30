package org.example.sktestpost.infra.adapter.out;

import org.example.sktestpost.application.port.out.MemberPersistOutPort;
import org.example.sktestpost.common.response.error.ErrorCode;
import org.example.sktestpost.common.response.exception.NotFoundException;
import org.example.sktestpost.domain.Member;
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

	@Override
	public Member findById(long memberId) {
		return memberJpaRepository.findById(memberId)
			.orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다.", ErrorCode.NOT_FOUND));
	}
}
