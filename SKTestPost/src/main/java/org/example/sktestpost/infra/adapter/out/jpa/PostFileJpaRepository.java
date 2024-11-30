package org.example.sktestpost.infra.adapter.out.jpa;

import org.example.sktestpost.domain.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFileJpaRepository extends JpaRepository<PostFile, Long> {
}
