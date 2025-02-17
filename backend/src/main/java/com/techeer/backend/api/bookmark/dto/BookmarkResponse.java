package com.techeer.backend.api.bookmark.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class BookmarkResponse {
    private final Long bookmarkId;
    private final Long resumeId;
    private final String resumeTitle;
    private final String resumeAuthor;
    private final LocalDateTime createdAt;
}
