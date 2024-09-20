package com.techeer.backend.domain.resume.entity;

import com.techeer.backend.domain.user.entity.User;
import com.techeer.backend.global.common.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "Resume")
public class Resume extends BaseEntity {
    @Id
    @Column(nullable = false, length = 255)
    private String id;

    // 하나의 유저가 다수의 이력서를 가진다.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 255)
    private String url;
}
