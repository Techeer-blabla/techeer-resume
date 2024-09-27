package com.techeer.backend.api.resume.repository;

import com.techeer.backend.api.resume.domain.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ResumeRepository extends PagingAndSortingRepository<Resume, Long>, JpaRepository<Resume, Long> {

    List<Resume> findByUsername(String username);
    Optional<Resume> findByIdAndDeletedAtIsNull(Long resumeId);
}
