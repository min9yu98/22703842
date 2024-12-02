package com.example.sktestpost.infra.adapter.out.jpa;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.sktestpost.common.entity.Refresh;

public interface RefreshJpaRepository extends CrudRepository<Refresh, String> {
	Optional<Refresh> findByMemberAccountId(String tokenAccountId);

	Boolean existsByRefreshToken(String refresh);
}
