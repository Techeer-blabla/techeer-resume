package com.techeer.backend.api.comment.repository;

import com.techeer.backend.api.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByResumeId(Long resumeId);
}
