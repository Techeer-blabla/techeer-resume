package com.techeer.backend.api.bookmark.converter;

import com.techeer.backend.api.bookmark.domain.Bookmark;
import com.techeer.backend.api.bookmark.dto.BookmarkResponse;

public class BookmarkConverter {

    public static BookmarkResponse toBookmarkResponse(Bookmark bookmark) {
        return BookmarkResponse.builder()
                .bookmarkId(bookmark.getId())
                .resumeId(bookmark.getResume().getId())
                .build();
    }
}
