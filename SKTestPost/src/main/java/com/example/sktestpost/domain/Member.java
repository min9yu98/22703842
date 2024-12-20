package com.example.sktestpost.domain;

import com.example.sktestpost.common.entity.BaseEntity;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String accountId;

	@Column(nullable = false)
	private String accountPwd;

	@Column(nullable = false)
	private String role;

	@Builder
	public Member(String name, String accountId, String accountPwd, String role) {
		this.name = name;
		this.accountId = accountId;
		this.accountPwd = accountPwd;
		this.role = role;
	}
}
