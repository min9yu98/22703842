package org.example.sktestpost.application.port.out;

import org.example.sktestpost.domain.Member;

public interface MemberPersistOutPort {
	Member save(Member creatingMember);
}
