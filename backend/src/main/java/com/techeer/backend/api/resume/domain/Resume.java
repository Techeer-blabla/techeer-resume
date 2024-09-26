package com.techeer.backend.api.resume.domain;

import com.techeer.backend.api.resume.dto.request.CreateResumeRequest;
import com.techeer.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "Resume")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Resume extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Long id;

    // 하나의 유저가 다수의 이력서를 가진다.
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
    @Column(name = "username")
    private String username;

    @Column(name = "resume_url")
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Position position;

    @Column(name = "career")
    private int career;

//    @ElementCollection
//    @Column(name = "applying_company")
//    private List<String> applyingCompany;

    @OneToMany(mappedBy = "resume")
    private List<ResumeTechStack> resumeTechStacks = new ArrayList<>();

    @Column(name = "resume_name", nullable = false)
    private String resumeName;

    // 이력서 pdf file 주소 update
    public void updateUrl(String url){
        if(url.isEmpty()){
            throw new IllegalArgumentException("null 입니다.");
        }
        this.url = url;
    }

    public Resume(CreateResumeRequest createResumeReq) {
        this.username = createResumeReq.getUsername();
        this.position = createResumeReq.getPosition();
        this.career = createResumeReq.getCareer();
        this.resumeName = "Resume of " + createResumeReq.getUsername();

        // 기술 스택 추가
        if (createResumeReq.getTechStack() != null) {
            createResumeReq.getTechStack().forEach(this::addTechStack);
        }
    }

    // 기술 스택 추가
    public void addTechStack(String techName) {
        TechStack techStack = new TechStack(techName); // 새로운 TechStack 생성
        ResumeTechStack resumeTechStack = new ResumeTechStack(this, techStack); // 중간 엔티티 생성
        this.resumeTechStacks.add(resumeTechStack); // 이력서에 기술 스택 추가
    }

}
