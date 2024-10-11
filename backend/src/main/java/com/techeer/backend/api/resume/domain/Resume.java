package com.techeer.backend.api.resume.domain;

import java.util.ArrayList;
import java.util.List;

import com.techeer.backend.api.resume.dto.request.CreateResumeRequest;
import com.techeer.backend.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Table(name = "Resume")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Resume extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 하나의 유저가 다수의 이력서를 가진다.
	//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	//    @JoinColumn(name = "user_id", nullable = false)
	//    private User user;
	@Column(name = "username")
	private String username;

	@Column(name = "resume_url")
	private String url;

	@Column(nullable = false)
	private String name;

	@Column(name = "career")
	private int career;

	@Enumerated(EnumType.STRING)
	@Column(name = "position")
	private Position position;

	@OneToMany(mappedBy = "resume")
	private List<ResumeTechStack> resumeTechStacks = new ArrayList<>();

	@Column(name = "s3_bucket_name")
	private String s3BucketName;  // S3 버킷 이름

	@Column(name = "s3_key")
	private String s3Key;  // S3 키 (경로)

	// 이력서 pdf file 주소 update
	public void updateUrl(String url) {
		if (url.isEmpty()) {
			throw new IllegalArgumentException("null 입니다.");
		}
		this.url = url;
	}

	@Builder
	public static Resume of(CreateResumeRequest createResumeRequest) {// Position 생성자는 상황에 맞게 수정 필요
		Resume resume = Resume.builder()
			.username(createResumeRequest.getUsername())
			.position(createResumeRequest.getPosition())
			.career(createResumeRequest.getCareer())
			.name("Resume of " + createResumeRequest.getUsername())
			.resumeTechStacks(new ArrayList<>())
			.build();

		// 필드를 추가할 때 별도의 연관 관계 설정 메서드 사용
		if (createResumeRequest.getTechStacks() != null) {
			createResumeRequest.getTechStacks().forEach(techStack -> resume.addTechStack(new ResumeTechStack(resume, techStack)));
		}

		return resume;
	}

	// 기술 스택 추가
	public void addTechStack(ResumeTechStack resumeTechStack) {
		resumeTechStacks.add(resumeTechStack);
	}

	public void updateS3Url(String url, String s3BucketName, String s3Key) {
		if (url == null || url.isEmpty()) {
			throw new IllegalArgumentException("URL이 null이거나 비어있습니다.");
		}
		this.url = url;
		this.s3BucketName = s3BucketName;
		this.s3Key = s3Key;
	}

}
