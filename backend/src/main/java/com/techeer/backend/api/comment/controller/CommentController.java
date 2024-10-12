package com.techeer.backend.api.comment.controller;

import com.techeer.backend.api.comment.dto.CommentCreateRequest;
import com.techeer.backend.api.comment.dto.CommentResponse;
import com.techeer.backend.api.comment.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="댓글 등록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments/")
public class CommentController {

  private final CommentService commentService;

  @PostMapping("/{resume_Id}/comments")
  public ResponseEntity<CommentResponse> createComment(
      @PathVariable("resume_Id") Long resumeId,
      @Valid @RequestBody CommentCreateRequest commentRequest) {

    CommentResponse commentResponse = commentService.createComment(resumeId, commentRequest);

    return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
  }
}
