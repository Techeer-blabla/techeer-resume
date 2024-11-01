package com.techeer.backend.api.bookmark.controller;

import com.techeer.backend.api.bookmark.dto.BookmarkRequest;
import com.techeer.backend.api.bookmark.dto.BookmarkResponse;
import com.techeer.backend.api.bookmark.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "북마크")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks/")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크 등록", description = "관심있는 이력서를 북마크로 등록합니다.")
    @PostMapping("/add")
    public ResponseEntity<BookmarkResponse> addBookmark(@RequestBody BookmarkRequest bookmarkRequest) {
        BookmarkResponse bookmarkResponse = bookmarkService.addBookmark(bookmarkRequest);
        return new ResponseEntity<>(bookmarkResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "북마크 삭제", description = "북마크를 해제합니다.")
    @DeleteMapping("/remove")
    public ResponseEntity<BookmarkResponse> removeBookmark(@RequestBody BookmarkRequest bookmarkRequest) {
        BookmarkResponse bookmarkResponse = bookmarkService.removeBookmark(bookmarkRequest);
        return new ResponseEntity<>(bookmarkResponse, HttpStatus.OK);
    }
}