package com.techeer.backend.domain.resume.dto.response;


import com.techeer.backend.domain.resume.entity.Resume;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ResumePageResponse {

    private List<ResumePageElement> elementList;
    private int totalPage;
    private int currentPage;

    private ResumePageResponse(List<ResumePageElement> elementList, int totalPages, int number) {
    }

    public static ResumePageResponse from(List<ResumePageElement> elementList , Page<Resume> page) {
        return new ResumePageResponse(elementList, page.getTotalPages(), page.getNumber());
    }
}
