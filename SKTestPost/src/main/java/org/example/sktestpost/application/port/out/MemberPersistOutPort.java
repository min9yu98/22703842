package org.example.sktestpost.application.port.out;

import org.example.sktestpost.domain.Member;

public interface MemberPersistOutPort {
	void save(Member creatingMember);

	Member findById(long memberId);

	Member findByAccountId(String accountId);

	boolean isExistsByAccountId(String accountId);
}
