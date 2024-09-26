package com.techeer.backend.domain.resume.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeTechStack {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    private TechStack techStack;

    public ResumeTechStack(Resume resume, TechStack techStack) {
        this.resume = resume;
        this.techStack = techStack;
    }
}
