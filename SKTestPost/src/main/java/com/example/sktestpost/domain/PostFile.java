package com.example.sktestpost.domain;

import org.hibernate.annotations.Where;

import com.example.sktestpost.common.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
public class PostFile extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String postFileUrl;

	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@Builder
	public PostFile(String postFileUrl, Post post) {
		this.postFileUrl = postFileUrl;
		this.post = post;
	}
}
