package com.techeer.backend.api.bookmark.controller;

import com.techeer.backend.api.bookmark.dto.BookmarkRequest;
import com.techeer.backend.api.bookmark.dto.BookmarkResponse;
import com.techeer.backend.api.bookmark.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Operation(summary = "북마크 조회", description = "사용자와 관련된 북마크를 모두 조회합니다.")
    @GetMapping("/{user_id}")
    public ResponseEntity<List<BookmarkResponse>> getBookmarksByUserId(@PathVariable Long user_id) {
        List<BookmarkResponse> bookmarks = bookmarkService.getBookmarksByUserId(user_id);
        return ResponseEntity.ok(bookmarks);
    }

    @Operation(summary = "단일 북마크 조회", description = "하나의 북마크만 검색해서 조회합니다.")
    @GetMapping("/bookmarks/{bookmark_id}")
    public ResponseEntity<BookmarkResponse> getBookmarkById(@PathVariable Long bookmark_id) {
        BookmarkResponse bookmark = bookmarkService.getBookmarkById(bookmark_id);
        return ResponseEntity.ok(bookmark);
    }
}