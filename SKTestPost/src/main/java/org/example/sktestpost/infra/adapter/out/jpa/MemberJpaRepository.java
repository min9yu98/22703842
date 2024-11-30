package org.example.sktestpost.infra.adapter.out.jpa;

import org.example.sktestpost.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
}
