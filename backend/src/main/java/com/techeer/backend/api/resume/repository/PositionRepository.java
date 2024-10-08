package com.techeer.backend.api.resume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techeer.backend.api.resume.domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
}
