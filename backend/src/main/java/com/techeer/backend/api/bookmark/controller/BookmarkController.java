package com.techeer.backend.api.bookmark.controller;

import com.techeer.backend.api.bookmark.dto.BookmarkAddRequest;
import com.techeer.backend.api.bookmark.dto.BookmarkResponse;
import com.techeer.backend.api.bookmark.service.BookmarkService;
import com.techeer.backend.global.common.response.CommonResponse;
import com.techeer.backend.global.success.SuccessCode;
import com.techeer.backend.global.success.SuccessResponse;
import com.techeer.backend.global.success.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    @PostMapping
    public ResponseEntity<CommonResponse<BookmarkResponse>> addBookmark(
            @RequestBody BookmarkAddRequest bookmarkRequest) {
        BookmarkResponse bookmarkResponse = bookmarkService.addBookmark(bookmarkRequest);
        return ResponseEntity.status(SuccessStatus.CREATED.getHttpStatus())
                .body(CommonResponse.of(SuccessStatus.CREATED, bookmarkResponse));
    }

    @Operation(summary = "북마크 삭제", description = "북마크를 해제합니다.")
    @DeleteMapping("/{bookmark_id}")
    public ResponseEntity<SuccessResponse> removeBookmark(@PathVariable("bookmark_id") Long bookmarkId) {
        bookmarkService.removeBookmark(bookmarkId);
        return ResponseEntity.status(SuccessCode.NO_CONTENT.getStatus()) // SuccessCode 사용
                .body(SuccessResponse.of(SuccessCode.NO_CONTENT));
    }


    @Operation(summary = "북마크 조회", description = "사용자와 관련된 북마크를 모두 조회합니다.")
    @GetMapping("/users/{user_id}")
    public ResponseEntity<CommonResponse<List<BookmarkResponse>>> getBookmarksByUserId(
            @PathVariable("user_id") Long userId) {
        List<BookmarkResponse> bookmarks = bookmarkService.getBookmarksByUserId(userId);
        return ResponseEntity.ok(CommonResponse.of(SuccessStatus.OK, bookmarks));
    }

    @Operation(summary = "단일 북마크 조회", description = "하나의 북마크만 검색해서 조회합니다.")
    @GetMapping("/{bookmark_id}")
    public ResponseEntity<CommonResponse<BookmarkResponse>> getBookmarkById(
            @PathVariable("bookmark_id") Long bookmarkId) {
        BookmarkResponse bookmark = bookmarkService.getBookmarkById(bookmarkId);
        return ResponseEntity.ok(CommonResponse.of(SuccessStatus.OK, bookmark));
    }
}
