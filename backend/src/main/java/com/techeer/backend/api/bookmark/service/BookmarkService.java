package com.techeer.backend.api.bookmark.service;

import com.techeer.backend.api.bookmark.converter.BookmarkConverter;
import com.techeer.backend.api.bookmark.domain.Bookmark;
import com.techeer.backend.api.bookmark.dto.BookmarkAddRequest;
import com.techeer.backend.api.bookmark.dto.BookmarkResponse;
import com.techeer.backend.api.bookmark.repository.BookmarkRepository;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.repository.ResumeRepository;
import com.techeer.backend.api.user.domain.User;
import com.techeer.backend.api.user.service.UserService;
import com.techeer.backend.global.error.ErrorStatus;
import com.techeer.backend.global.error.exception.BusinessException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final ResumeRepository resumeRepository;
    private final UserService userService;

    @Transactional
    public BookmarkResponse addBookmark(BookmarkAddRequest bookmarkRequest) {

        User user = userService.getLoginUser();

        Resume resume = resumeRepository.findById(bookmarkRequest.getResumeId())
                .orElseThrow(() -> new BusinessException(ErrorStatus.RESUME_NOT_FOUND));

        Bookmark bookmark = Bookmark.of(resume, user);
        bookmarkRepository.save(bookmark);

        return BookmarkConverter.toBookmarkResponse(bookmark);
    }

    @Transactional
    public BookmarkResponse removeBookmark(Long bookmarkId) {
        // bookmark_id로 북마크 조회
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new BusinessException(ErrorStatus.BOOKMARK_NOT_FOUND));

        // 북마크 삭제
        bookmarkRepository.delete(bookmark);

        return BookmarkConverter.toBookmarkResponse(bookmark);
    }

    // user_id로 모든 북마크 조회
    public List<BookmarkResponse> getBookmarksByUserId(Long userId) {

        List<Bookmark> bookmarks = bookmarkRepository.findByUserId(userId);
        if (bookmarks.isEmpty()) {
            throw new BusinessException(ErrorStatus.BOOKMARKS_NOT_FOUND);
        }

        return bookmarks.stream()
                .map(BookmarkConverter::toBookmarkResponse)
                .collect(Collectors.toList());
    }

    // bookmark_id로 단일 북마크 조회
    public BookmarkResponse getBookmarkById(Long bookmarkId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new BusinessException(ErrorStatus.BOOKMARK_NOT_FOUND));

        return BookmarkConverter.toBookmarkResponse(bookmark);
    }
}
