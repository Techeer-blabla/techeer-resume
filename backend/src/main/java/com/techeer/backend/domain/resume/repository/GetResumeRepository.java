package com.techeer.backend.domain.resume.repository;

import com.techeer.backend.domain.resume.entity.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface GetResumeRepository extends JpaRepository<Resume, Long>, JpaSpecificationExecutor<Resume> {
    @Query("SELECT r FROM Resume r WHERE r.deletedAt IS NULL")
    Page<Resume> findAllActiveResumes(Specification<Resume> spec, Pageable pageable);

}
