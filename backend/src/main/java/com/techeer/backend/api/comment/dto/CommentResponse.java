package com.techeer.backend.api.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class CommentResponse {

  private final Long commentId;
  private final Long resumeId;
  private final String content;
}
