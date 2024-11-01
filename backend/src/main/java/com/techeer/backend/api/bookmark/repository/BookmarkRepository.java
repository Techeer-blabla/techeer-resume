package com.techeer.backend.api.bookmark.repository;

import com.techeer.backend.api.bookmark.domain.Bookmark;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserIdAndResumeId(Long userId, Long resumeId);

    List<Bookmark> findByUserId(Long userId);
}
