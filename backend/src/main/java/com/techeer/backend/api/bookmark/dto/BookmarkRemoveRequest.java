package com.techeer.backend.api.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookmarkRemoveRequest {
    private Long userId;
    private Long resumeId;
    private Long bookmarkId;
}
