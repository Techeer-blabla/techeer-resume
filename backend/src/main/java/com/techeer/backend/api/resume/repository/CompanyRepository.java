package com.techeer.backend.api.resume.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techeer.backend.api.resume.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
	Optional<Company> findByName(String name);
}
