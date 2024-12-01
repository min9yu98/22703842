package com.example.sktestpost.domain;

import com.example.sktestpost.common.entity.BaseEntity;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
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
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	private Long viewCount = 0L;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Builder
	public Post(String title, String content, Member member) {
		this.title = title;
		this.content = content;
		this.member = member;
	}

	public void updatePost(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public void increaseViewCount() {
		this.viewCount++;
	}
}
