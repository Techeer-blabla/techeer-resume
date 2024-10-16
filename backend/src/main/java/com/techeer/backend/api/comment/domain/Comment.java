package com.techeer.backend.api.comment.domain;

import com.techeer.backend.api.comment.dto.CommentCreateRequest;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name="댓글")
@Builder
@AllArgsConstructor
public class Comment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Long id;

  @Column(nullable = false, length = 255)
  private String content;


  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "resume_id", nullable = false)
  private Resume resume;

  protected Comment() {
  }
  // 댓글 내용 수정
  public void updateContent(String content){
    this.content = content;
  }

  public static Comment of(Resume resume, CommentCreateRequest commentCreateRequest) {
    return Comment.builder()
        .resume(resume)
        .content(commentCreateRequest.getContent())
        .build();
  }
}
