package com.techeer.backend.api.comment.service;

import com.techeer.backend.api.comment.domain.Comment;
import com.techeer.backend.api.comment.domain.CommentConverter;
import com.techeer.backend.api.comment.dto.CommentCreateRequest;
import com.techeer.backend.api.comment.dto.CommentResponse;
import com.techeer.backend.api.comment.repository.CommentRepository;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.repository.ResumeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private static final String RESUME_NOT_FOUND_MESSAGE = "Resume not found";

  private final CommentRepository commentRepository;
  private final ResumeRepository resumeRepository;

  @Transactional
  public CommentResponse createComment(Long resumeId, CommentCreateRequest commentCreateRequest){
    Resume resume = resumeRepository.findByIdAndDeletedAtIsNull(resumeId)
        .orElseThrow(() -> new EntityNotFoundException(RESUME_NOT_FOUND_MESSAGE));

    Comment comment = Comment.of(resume, commentCreateRequest);
    commentRepository.save(comment);

    return CommentConverter.of(resume, comment);
  }
}
