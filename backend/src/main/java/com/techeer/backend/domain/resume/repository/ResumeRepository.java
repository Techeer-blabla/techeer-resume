package com.techeer.backend.domain.resume.repository;

import com.techeer.backend.domain.resume.entity.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResumeRepository extends PagingAndSortingRepository<Resume, Long>, JpaRepository<Resume, Long> {

    List<Resume> findByUserUsername(String username);
    List<Resume> findByPosition(Resume.Position position);
    List<Resume> findByTechStack(Resume.TechStack techStack);
    Page<Resume> findByResumePage(Pageable pageable);
}
