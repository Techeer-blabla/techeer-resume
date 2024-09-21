package com.techeer.backend.domain.resume.entity;

import com.techeer.backend.domain.resume.dto.request.ResumeReq;
import com.techeer.backend.domain.user.entity.User;
import com.techeer.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "Resume")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resume extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private String id;

    // 하나의 유저가 다수의 이력서를 가진다.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "resume_url")
    private String url;

    @ElementCollection
    @Column(name = "position")
    private List<String> position;

    @Column(name = "career")
    private int career;

    @ElementCollection
    @Column(name = "applying_company")
    private List<String> applyingCompany;

    @ElementCollection
    @Column(name = "tech_stack")
    private List<String> techStack;

    // 이력서 pdf file 주소 update
    public void urlUpdate(String url){
        if(url.isEmpty()){
            throw new IllegalArgumentException("null 입니다.");
        }
        this.url = url;
    }

    private Resume(User user, ResumeReq req, String url){
        this.user = user;
        this.url = url;
        this.position = req.getPosition();
        this.career = req.getCareer();
        this.applyingCompany = req.getApplyingCompany();
        this.techStack = req.getTechStack();
    }

    // 이력서 등록시 정적 메소드
    public static Resume createResume(User user, ResumeReq req, String url){
        return new Resume(user,req,url);
    }
}
