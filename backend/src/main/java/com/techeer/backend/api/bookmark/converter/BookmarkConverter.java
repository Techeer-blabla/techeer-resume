package com.techeer.backend.api.bookmark.converter;

import com.techeer.backend.api.bookmark.domain.Bookmark;
import com.techeer.backend.api.bookmark.dto.BookmarkResponse;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.user.domain.User;

public class BookmarkConverter {

    public static BookmarkResponse toBookmarkResponse(Bookmark bookmark) {
        return BookmarkResponse.builder()
                .bookmarkId(bookmark.getId())
                .userId(bookmark.getUser().getId())
                .resumeId(bookmark.getResume().getId())
                .build();
    }

    public static Bookmark toBookmarkEntity(User user, Resume resume) {
        return Bookmark.builder()
                .user(user)
                .resume(resume)
                .build();
    }
}
