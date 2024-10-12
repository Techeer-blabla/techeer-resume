package com.techeer.backend.api.resume.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ManyToMany(mappedBy = "companies")
	private List<Resume> resumes = new ArrayList<>();

	@Builder
	public Company(String name) {
		this.name = name;
		this.resumes = new ArrayList<>();
	}

	public void addResume(Resume resume) {
		if (resume == null) {
			throw new IllegalArgumentException("Resume은 null일 수 없습니다.");
		}
		resumes = new ArrayList<>();
		// 이미 추가된 이력서인지 확인 후 추가
		resumes.add(resume);
	}
}
