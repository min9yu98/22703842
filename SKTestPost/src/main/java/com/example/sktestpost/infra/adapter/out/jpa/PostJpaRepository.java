package com.example.sktestpost.infra.adapter.out.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.sktestpost.domain.Post;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

	@Query("select p from Post p where lower(p.title) like %:keyword% or lower(p.member.accountId) like %:keyword% order by p.createdAt desc")
	Page<Post> findAllByKeyword(Pageable pageable, String keyword);

	@Query("select p from Post p where lower(p.title) like %:keyword% order by p.createdAt desc")
	Page<Post> findAllByTitle(Pageable pageable, String keyword);

	@Query("select p from Post p where lower(p.member.accountId) like %:keyword% order by p.createdAt desc")
	Page<Post> findAllByMemberAccountId(Pageable pageable, String keyword);
	
}
