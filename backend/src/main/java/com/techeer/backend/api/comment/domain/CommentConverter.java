package com.techeer.backend.api.comment.domain;

import com.techeer.backend.api.comment.dto.CommentResponse;
import com.techeer.backend.api.resume.domain.Resume;

public class CommentConverter {

  public static CommentResponse of(Resume resume, Comment comment) {
    return CommentResponse.builder()
        .commentId(comment.getId())
        .resumeId(resume.getId())
        .content(comment.getContent())
        .build();
  }
}
