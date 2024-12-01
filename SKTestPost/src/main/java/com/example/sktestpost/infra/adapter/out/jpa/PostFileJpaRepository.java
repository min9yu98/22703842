package com.example.sktestpost.infra.adapter.out.jpa;

import com.example.sktestpost.domain.PostFile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFileJpaRepository extends JpaRepository<PostFile, Long> {
}
