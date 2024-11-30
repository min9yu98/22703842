package org.example.sktestpost.application.port.out;

import org.example.sktestpost.common.domain.Member;

public interface MemberPersistOutPort {
	Member save(Member creatingMember);
}
