package com.techeer.backend.domain.resume.repository;

import com.techeer.backend.domain.resume.dto.request.ResumeSearchRequest;
import com.techeer.backend.domain.resume.entity.Position;
import com.techeer.backend.domain.resume.entity.Resume;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ResumeSpecification {

    public static Specification<Resume> search(ResumeSearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            // 포지션 필터링
            if (request.getPositions() != null && !request.getPositions().isEmpty()) {
                Expression<Position> positionExpression = root.get("position");
                predicate = criteriaBuilder.and(predicate, positionExpression.in(request.getPositions()));
            }

            // 경력 범위 필터링
            if (request.getMinCareer() != null && request.getMaxCareer() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.between(root.get("career"), request.getMinCareer(), request.getMaxCareer()));
            } else if (request.getMinCareer() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("career"), request.getMinCareer()));
            } else if (request.getMaxCareer() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.lessThanOrEqualTo(root.get("career"), request.getMaxCareer()));
            }

            // 기술 스택 필터링 (선택 사항)
            if (request.getTechStacks() != null && !request.getTechStacks().isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        root.join("techStacks").in(request.getTechStacks()));
            }

            return predicate;
        };
    }
}
