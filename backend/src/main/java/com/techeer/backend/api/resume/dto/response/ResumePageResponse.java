package com.techeer.backend.api.resume.dto.response;


import com.techeer.backend.api.resume.domain.Resume;
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
        this.elementList = elementList;
        this.totalPage = totalPages;
        this.currentPage = number;
    }

    public static ResumePageResponse from(List<ResumePageElement> elementList , Page<Resume> page) {
        return new ResumePageResponse(elementList, page.getTotalPages(), page.getNumber());
    }
}
