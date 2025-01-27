package com.techeer.backend.api.resume.service.facade;

import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.domain.ResumePdf;
import com.techeer.backend.api.resume.dto.request.CreateResumeRequest;
import com.techeer.backend.api.resume.service.ResumePdfService;
import com.techeer.backend.api.resume.service.ResumeService;
import com.techeer.backend.api.tag.company.domain.Company;
import com.techeer.backend.api.tag.company.domain.ResumeCompany;
import com.techeer.backend.api.tag.company.service.CompanyService;
import com.techeer.backend.api.tag.techStack.domain.ResumeTechStack;
import com.techeer.backend.api.tag.techStack.domain.TechStack;
import com.techeer.backend.api.tag.techStack.service.TechStackService;
import com.techeer.backend.api.user.domain.User;
import com.techeer.backend.api.user.service.UserService;
import com.techeer.backend.global.error.ErrorCode;
import com.techeer.backend.global.error.exception.BusinessException;
import com.techeer.backend.global.lock.DistributedLockService;
import com.techeer.backend.global.ratelimiter.RateLimiterService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ResumeCreateFacade {

    // 서비스 의존관계 주입
    private final ResumeService resumeService;
    private final CompanyService companyService;
    private final TechStackService techStackService;
    private final ResumePdfService resumePdfService;
    private final UserService userService;
    private final DistributedLockService distributedLockService;
    private final RateLimiterService rateLimiterService;

    // 이력서 생성
    @Transactional
    public void createResume(CreateResumeRequest req, MultipartFile multipartFile) {
        User user = userService.getLoginUser();
        String lockKey = "resume_creation_lock_" + user.getId();

        RLock lock = distributedLockService.acquireLock(lockKey, 5, 60, TimeUnit.SECONDS);
        if (lock == null) {
            throw new BusinessException(ErrorCode.RESUME_UPLOAD_FAILED);
        }

        try {
            String counterKey = "resume_creation_counter" + user.getId();
            Long count = rateLimiterService.incrementAndCheckLimit(counterKey, 5, 1, TimeUnit.MINUTES);

            if (count > 5) {
                throw new BusinessException(ErrorCode.RESUME_UPLOAD_FAILED);
            }

            // 이력서 생성 로직 수행
            List<TechStack> techStacks = techStackService.findOrCreateTechStacks(req.getTechStackNames());
            List<Company> companies = companyService.findOrCreateCompanies(req.getCompanyNames());
            Resume previousResume = resumeService.findLatestByUser(user);

            Resume resume = Resume.builder()
                    .user(user)
                    .position(req.getPosition())
                    .career(req.getCareer())
                    .name("Resume of " + user.getUsername() + " - " + LocalDate.now(ZoneId.of("Asia/Seoul")))
                    .previousResumeId(previousResume != null ? previousResume.getId() : null)
                    .build();

            resumeService.saveResume(resume);

            if (previousResume != null) {
                previousResume.updateLaterResumeId(resume.getId());
            }

            addResumeTechStacks(resume, techStacks);
            addResumeCompanies(resume, companies);

            ResumePdf resumePdf = resumePdfService.saveResumePdf(resume, multipartFile);
            resume.addResumePdf(resumePdf);

            user.addResume(resume);
        } finally {
            // 락 해제
            distributedLockService.releaseLock(lock);
        }
    }

    private void addResumeTechStacks(Resume resume, List<TechStack> techStacks) {
        techStacks.forEach(techStack -> {
            ResumeTechStack resumeTechStack = new ResumeTechStack(resume, techStack);
            resume.addResumeTechStack(resumeTechStack);
        });
    }

    private void addResumeCompanies(Resume resume, List<Company> companies) {
        companies.forEach(company -> {
            ResumeCompany resumeCompany = new ResumeCompany(resume, company);
            resume.addResumeCompany(resumeCompany);
        });
    }
}
