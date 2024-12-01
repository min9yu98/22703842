package org.example.sktestpost.infra.adapter.out.jpa;

import org.example.sktestpost.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

	@Query("select p from Post p where lower(p.title) like %:keyword% or lower(p.member.accountId) like %:keyword% order by p.createdAt desc")
	Page<Post> findAllByKeyword(Pageable pageable, String keyword);
}
