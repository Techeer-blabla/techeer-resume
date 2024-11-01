package com.techeer.backend.api.bookmark.repository;

import com.techeer.backend.api.bookmark.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
  Optional<Bookmark> findByUserIdAndResumeId(Long userId, Long resumeId);

}
