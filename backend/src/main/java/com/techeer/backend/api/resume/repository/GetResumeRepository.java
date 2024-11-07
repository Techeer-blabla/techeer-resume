package com.techeer.backend.api.resume.repository;

import com.techeer.backend.api.resume.domain.Resume;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GetResumeRepository extends JpaRepository<Resume, Long> {

    @Query(value = "SELECT DISTINCT r FROM Resume r " +
            "WHERE r.career BETWEEN :minCareer AND :maxCareer " +
            "AND (:techStackNames IS NULL OR EXISTS (" +
            "SELECT 1 FROM ResumeTechStack rts " +
            "WHERE rts.resume = r AND rts.techStack.name IN :techStackNames)) " +
            "AND (:companyNames IS NULL OR EXISTS (" +
            "SELECT 1 FROM ResumeCompany rc " +
            "WHERE rc.resume = r AND rc.company.name IN :companyNames))",
            countQuery = "SELECT COUNT(DISTINCT r) FROM Resume r " +
                    "WHERE r.career BETWEEN :minCareer AND :maxCareer " +
                    "AND (:techStackNames IS NULL OR EXISTS (" +
                    "SELECT 1 FROM ResumeTechStack rts " +
                    "WHERE rts.resume = r AND rts.techStack.name IN :techStackNames)) " +
                    "AND (:companyNames IS NULL OR EXISTS (" +
                    "SELECT 1 FROM ResumeCompany rc " +
                    "WHERE rc.resume = r AND rc.company.name IN :companyNames))")
    Page<Resume> findResumesByCriteria(@Param("minCareer") int minCareer,
                                       @Param("maxCareer") int maxCareer,
                                       @Param("techStackNames") List<String> techStackNames,
                                       @Param("companyNames") List<String> companyNames,
                                       Pageable pageable);
}

