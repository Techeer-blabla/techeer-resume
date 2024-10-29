package com.techeer.backend.api.bookmark.service;

import com.techeer.backend.api.bookmark.converter.BookmarkConverter;
import com.techeer.backend.api.bookmark.domain.Bookmark;
import com.techeer.backend.api.bookmark.dto.BookmarkRequest;
import com.techeer.backend.api.bookmark.dto.BookmarkResponse;
import com.techeer.backend.api.bookmark.repository.BookmarkRepository;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.repository.ResumeRepository;
import com.techeer.backend.api.user.domain.User;
import com.techeer.backend.api.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {

  private final BookmarkRepository bookmarkRepository;
  private final ResumeRepository resumeRepository;
  private final UserRepository userRepository;

  @Transactional
  public BookmarkResponse addBookmark(BookmarkRequest bookmarkRequest){

    User user = userRepository.findById(bookmarkRequest.getUserId())
        .orElseThrow(() -> new IllegalArgumentException("user not found"));
    Resume resume =resumeRepository.findById(bookmarkRequest.getResumeId())
        .orElseThrow(() -> new IllegalArgumentException("resume not found"));

    Bookmark bookmark = Bookmark.of(resume, user);
    bookmarkRepository.save(bookmark);

    return BookmarkConverter.toBookmarkResponse(bookmark);
  }
}
