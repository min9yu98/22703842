package com.example.sktestpost.infra.adapter.out.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sktestpost.domain.PostFile;

public interface PostFileJpaRepository extends JpaRepository<PostFile, Long> {
	PostFile findByPostId(Long postId);

	List<PostFile> findAllByPostId(Long postId);
}
