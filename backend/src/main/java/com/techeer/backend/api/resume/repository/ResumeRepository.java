package com.techeer.backend.api.resume.repository;

import com.techeer.backend.api.resume.domain.Resume;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    @Query("SELECT r FROM Resume r WHERE r.user.username = :username")
    List<Resume> findResumesByUsername(@Param("username") String username);

    Optional<Resume> findByIdAndDeletedAtIsNull(Long resumeId);
}
