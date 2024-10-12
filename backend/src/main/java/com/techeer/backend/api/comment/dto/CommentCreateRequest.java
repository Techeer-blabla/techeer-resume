package com.techeer.backend.api.comment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

  @NotBlank(message="content는 필수입니다.")
  private String content;
  @JsonCreator
  public CommentCreateRequest(String content) {
    this.content = content;
  }
}
