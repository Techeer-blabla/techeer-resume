package com.techeer.backend.api.resume.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.techeer.backend.api.bookmark.domain.Bookmark;
import com.techeer.backend.api.bookmark.repository.BookmarkRepository;
import com.techeer.backend.api.bookmark.service.BookmarkService;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.repository.ResumeRepository;
import com.techeer.backend.api.tag.position.Position;
import com.techeer.backend.api.user.domain.Role;
import com.techeer.backend.api.user.domain.SocialType;
import com.techeer.backend.api.user.domain.User;
import com.techeer.backend.global.error.ErrorCode;
import com.techeer.backend.global.error.exception.BusinessException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookmarkServiceTest {

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private ResumeRepository resumeRepository;

    @InjectMocks
    private BookmarkService bookmarkService;

    private User testUser;
    private Resume testResume;
    private Bookmark testBookmark;

    @BeforeEach
    void setUp() {
        // User 객체 생성 (빌더 패턴 사용) - id 설정
        testUser = User.builder()
                .id(1L) // id 설정
                .email("john@example.com")
                .username("JohnDoe")
                .role(Role.TECHEER)
                .socialType(SocialType.GOOGLE)
                .build();

        // Resume 객체 생성 (빌더 패턴 사용)
        testResume = Resume.builder()
                .id(1L)
                .user(testUser)
                .name("John's Resume")
                .career(5)
                .position(Position.DEVOPS)
                .build();

        // Bookmark 객체 생성 (빌더 패턴 사용)
        testBookmark = Bookmark.builder()
                .id(1L)
                .user(testUser)
                .resume(testResume)
                .build();
    }

    @Nested
    @DisplayName("addBookmark 메서드 테스트")
    class AddBookmarkTest {

        @Test
        @DisplayName("Given 유효한 사용자와 이력서 ID로 북마크 추가 요청, When 북마크 생성 요청, Then 북마크가 성공적으로 생성된다.")
        void addBookmark_Success() {
            // Given
            Long resumeId = testResume.getId();
            when(resumeRepository.findById(resumeId)).thenReturn(Optional.of(testResume));
            when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(testBookmark);

            // When
            Bookmark createdBookmark = bookmarkService.addBookmark(testUser, resumeId);

            // Then
            assertThat(createdBookmark).isNotNull();
            assertThat(createdBookmark.getId()).isEqualTo(testBookmark.getId());
            assertThat(createdBookmark.getUser().getId()).isEqualTo(testUser.getId());
            assertThat(createdBookmark.getResume().getId()).isEqualTo(testResume.getId());

            verify(resumeRepository, times(1)).findById(resumeId);
            verify(bookmarkRepository, times(1)).save(any(Bookmark.class));
        }

        @Test
        @DisplayName("Given 존재하지 않는 이력서 ID로 북마크 추가 요청, When 북마크 생성 요청, Then RESUME_NOT_FOUND 예외가 발생한다.")
        void addBookmark_ResumeNotFound() {
            // Given
            Long invalidResumeId = 999L;
            when(resumeRepository.findById(invalidResumeId)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> bookmarkService.addBookmark(testUser, invalidResumeId))
                    .isInstanceOf(BusinessException.class)
                    .hasMessageContaining(ErrorCode.RESUME_NOT_FOUND.getMessage());

            verify(resumeRepository, times(1)).findById(invalidResumeId);
            verify(bookmarkRepository, never()).save(any(Bookmark.class));
        }
    }

    @Nested
    @DisplayName("removeBookmark 메서드 테스트")
    class RemoveBookmarkTest {

        @Test
        @DisplayName("Given 유효한 사용자와 북마크 ID로 북마크 제거 요청, When 북마크 제거 요청, Then 북마크가 성공적으로 제거된다.")
        void removeBookmark_Success() {
            // Given
            Long bookmarkId = testBookmark.getId();
            when(bookmarkRepository.findById(bookmarkId)).thenReturn(Optional.of(testBookmark));

            // When & Then
            assertThatCode(() -> bookmarkService.removeBookmark(testUser, bookmarkId))
                    .doesNotThrowAnyException();

            verify(bookmarkRepository, times(1)).findById(bookmarkId);
            verify(bookmarkRepository, times(1)).delete(testBookmark);
        }

        @Test
        @DisplayName("Given 존재하지 않는 북마크 ID로 제거 요청, When 북마크 제거 요청, Then BOOKMARK_NOT_FOUND 예외가 발생한다.")
        void removeBookmark_BookmarkNotFound() {
            // Given
            Long invalidBookmarkId = 999L;
            when(bookmarkRepository.findById(invalidBookmarkId)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> bookmarkService.removeBookmark(testUser, invalidBookmarkId))
                    .isInstanceOf(BusinessException.class)
                    .hasMessageContaining(ErrorCode.BOOKMARK_NOT_FOUND.getMessage());

            verify(bookmarkRepository, times(1)).findById(invalidBookmarkId);
            verify(bookmarkRepository, never()).delete(any(Bookmark.class));
        }

        @Test
        @DisplayName("Given 다른 사용자의 북마크 ID로 제거 요청, When 북마크 제거 요청, Then UNAUTHORIZED 예외가 발생한다.")
        void removeBookmark_Unauthorized() {
            // Given
            User otherUser = User.builder()
                    .id(2L) // 다른 사용자 ID 설정
                    .email("jane@example.com")
                    .username("JaneDoe")
                    .role(Role.TECHEER)
                    .socialType(SocialType.GITHUB)
                    .build();

            Bookmark otherBookmark = Bookmark.builder()
                    .id(testBookmark.getId())
                    .user(otherUser)
                    .resume(testResume)
                    .build();

            when(bookmarkRepository.findById(testBookmark.getId())).thenReturn(Optional.of(otherBookmark));

            // When & Then
            assertThatThrownBy(() -> bookmarkService.removeBookmark(testUser, testBookmark.getId()))
                    .isInstanceOf(BusinessException.class)
                    .hasMessageContaining(ErrorCode.UNAUTHORIZED.getMessage());

            verify(bookmarkRepository, times(1)).findById(testBookmark.getId());
            verify(bookmarkRepository, never()).delete(any(Bookmark.class));
        }
    }

    @Nested
    @DisplayName("getBookmarksByUserId 메서드 테스트")
    class GetBookmarksByUserIdTest {

        @Test
        @DisplayName("Given 사용자가 북마크를 가지고 있는 경우, When 북마크 조회 요청, Then 북마크 리스트가 반환된다.")
        void getBookmarksByUserId_WithBookmarks() {
            // Given
            Long userId = testUser.getId();
            List<Bookmark> bookmarks = Collections.singletonList(testBookmark);
            when(bookmarkRepository.findAllByUserId(userId)).thenReturn(bookmarks);

            // When
            List<Bookmark> result = bookmarkService.getBookmarksByUserId(userId);

            // Then
            assertThat(result).isNotNull();
            assertThat(result).isNotEmpty();
            assertThat(result).hasSize(1);
            assertThat(result.get(0)).isEqualTo(testBookmark);

            verify(bookmarkRepository, times(1)).findAllByUserId(userId);
        }

        @Test
        @DisplayName("Given 사용자가 북마크를 가지고 있지 않은 경우, When 북마크 조회 요청, Then 빈 리스트가 반환된다.")
        void getBookmarksByUserId_NoBookmarks() {
            // Given
            Long userId = testUser.getId();
            when(bookmarkRepository.findAllByUserId(userId)).thenReturn(Collections.emptyList());

            // When
            List<Bookmark> result = bookmarkService.getBookmarksByUserId(userId);

            // Then
            assertThat(result).isNotNull();
            assertThat(result).isEmpty();

            verify(bookmarkRepository, times(1)).findAllByUserId(userId);
        }
    }
}
