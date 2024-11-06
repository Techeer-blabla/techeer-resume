package com.techeer.backend.api.resume.service.facade;

import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.domain.ResumePdf;
import com.techeer.backend.api.resume.dto.request.CreateResumeRequest;
import com.techeer.backend.api.resume.service.ResumePdfService;
import com.techeer.backend.api.resume.service.ResumeService;
import com.techeer.backend.api.tag.company.domain.Company;
import com.techeer.backend.api.tag.company.domain.ResumeCompany;
import com.techeer.backend.api.tag.company.service.CompanyService;
import com.techeer.backend.api.tag.position.Position;
import com.techeer.backend.api.tag.techStack.domain.ResumeTechStack;
import com.techeer.backend.api.tag.techStack.domain.TechStack;
import com.techeer.backend.api.tag.techStack.service.TechStackService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ResumeCreateFacade {

    // service 의존관걔 주입
    private final ResumeService resumeService;
    private final CompanyService companyService;
    private final TechStackService techStackService;
    private final ResumePdfService resumePdfService;

    // 이력서 생성
    public void createResume(CreateResumeRequest req, MultipartFile multipartFile) {

        // 관련 기술 처리
        List<TechStack> techStacks = techStackService.findOrCreateTechStacks(req.getTechStackNames());

        // 타겟 회사 처리
        List<Company> companies = companyService.findOrCreateCompanies(req.getCompanyNames());

        // 이력서 엔티티 생성
        Resume resume = Resume.builder()
                .username(req.getUsername())
                .position(Position.valueOf(req.getPosition()))
                .career(req.getCareer())
                .name("Resume of " + req.getUsername())
                .build();

        resumeService.saveResume(resume);

        // ResumeTechStack 생성 및 이력서에 추가
        techStacks.forEach(techStack -> {
            ResumeTechStack resumeTechStack = new ResumeTechStack(resume, techStack);
            resume.addResumeTechStack(resumeTechStack);
        });

        // ResumeCompany 생성 및 이력서에 추가
        companies.forEach(company -> {
            ResumeCompany resumeCompany = new ResumeCompany(resume, company);
            resume.addResumeCompany(resumeCompany);
        });

        ResumePdf resumePdf = resumePdfService.saveResumePdf(resume, multipartFile);
        resume.addResumePdf(resumePdf);
    }

}
