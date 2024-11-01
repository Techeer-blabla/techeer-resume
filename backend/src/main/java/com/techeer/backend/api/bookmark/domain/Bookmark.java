package com.techeer.backend.api.bookmark.domain;

import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.user.domain.User;
import com.techeer.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@AllArgsConstructor
@Table(name = "BOOKMARK")
@Getter
@Builder
@Entity
public class Bookmark extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "resume_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Resume resume;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  // 기본 생성자
  protected Bookmark() {}

  // Builder 메서드
  public static Bookmark of(Resume resume, User user) {
    return Bookmark.builder()
        .resume(resume)
        .user(user)
        .build();
  }
}