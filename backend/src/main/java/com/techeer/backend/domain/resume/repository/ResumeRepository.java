package com.techeer.backend.domain.resume.repository;

import com.techeer.backend.domain.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends PagingAndSortingRepository<Resume, Long>, JpaRepository<Resume, Long> {

}
