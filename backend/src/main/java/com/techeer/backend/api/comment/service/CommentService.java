package com.techeer.backend.api.comment.service;

import com.techeer.backend.api.comment.domain.Comment;
import com.techeer.backend.api.comment.domain.CommentConverter;
import com.techeer.backend.api.comment.dto.CommentCreateRequest;
import com.techeer.backend.api.comment.dto.CommentResponse;
import com.techeer.backend.api.comment.repository.CommentRepository;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.repository.ResumeRepository;
import com.techeer.backend.global.error.exception.comment.CommentNotFoundException;
import com.techeer.backend.global.error.exception.comment.NoCommentsForResumeException;
import com.techeer.backend.global.error.exception.resume.ResumeNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final ResumeRepository resumeRepository;

  @Transactional
  public CommentResponse createComment(Long resumeId, CommentCreateRequest commentCreateRequest){
    Resume resume = resumeRepository.findByIdAndDeletedAtIsNull(resumeId)
        .orElseThrow(ResumeNotFoundException::new);

    Comment comment = Comment.of(resume, commentCreateRequest);
    commentRepository.save(comment);

    return CommentConverter.of(resume, comment);
  }
  @Transactional
  public CommentResponse getCommentById(Long commentId){
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(CommentNotFoundException::new);

    return CommentConverter.of(comment.getResume(), comment);
  }
  @Transactional
  public List<CommentResponse> getCommentByResumeId(Long resumeId) {
    Resume resume = resumeRepository.findByIdAndDeletedAtIsNull(resumeId)
        .orElseThrow(ResumeNotFoundException::new);
    List<Comment> comments = commentRepository.findByResumeId(resumeId);

    if(comments.isEmpty()){
      throw new NoCommentsForResumeException();
    }

    return comments.stream()
        .map(comment -> CommentConverter.of(resume, comment))
        .collect(Collectors.toList());
  }
}
