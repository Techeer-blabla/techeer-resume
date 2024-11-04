package com.techeer.backend.api.comment.converter;

import com.techeer.backend.api.comment.domain.Comment;
import com.techeer.backend.api.comment.dto.CommentCreateRequest;
import com.techeer.backend.api.comment.dto.CommentResponse;
import com.techeer.backend.api.resume.domain.Resume;

public class CommentConverter {

    public static CommentResponse toCommentResponse(Resume resume, Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .resumeId(resume.getId())
                .content(comment.getContent())
                .build();
    }

    public static Comment toComment(Resume resume, CommentCreateRequest commentCreateRequest) {
        return Comment.builder()
                .resume(resume)
                .content(commentCreateRequest.getContent())
                .build();
    }
}
