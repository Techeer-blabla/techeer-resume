package com.techeer.backend.api.comment.controller;

import com.techeer.backend.api.comment.dto.CommentCreateRequest;
import com.techeer.backend.api.comment.dto.CommentResponse;
import com.techeer.backend.api.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="댓글 등록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments/")
public class CommentController {

  private final CommentService commentService;

  @PostMapping("/{resume_id}/comments")
  public ResponseEntity<CommentResponse> createComment(
      @PathVariable("resume_id") Long resumeId,
      @Valid @RequestBody CommentCreateRequest commentRequest) {

    CommentResponse commentResponse = commentService.createComment(resumeId, commentRequest);

    return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
  }

  //단일 댓글 조회
  @Operation(summary="단일 댓글 조회", description = "하나의 댓글만 조회하는 기능")
  @GetMapping("/{resume_Id}/comments/{comment_id}")
  public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long comment_id){
    CommentResponse response = commentService.getCommentById(comment_id);
    return ResponseEntity.ok(response);
  }

  // 이력서 관련 댓글 조회
  @Operation(summary = "이력서 관련 댓글 조회", description = "이력서에 작성되어 있는 모든 댓글을 조회하는 기능")
  @GetMapping("/{resume_id}/comments")
  public ResponseEntity<List<CommentResponse>> getCommentByResumeId(@PathVariable Long resume_id) {
    List<CommentResponse> responses = commentService.getCommentByResumeId(resume_id);
    return ResponseEntity.ok(responses);
  }
}
