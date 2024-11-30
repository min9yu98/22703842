package org.example.sktestpost.infra.adapter.out.jpa;

import org.example.sktestpost.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
}
