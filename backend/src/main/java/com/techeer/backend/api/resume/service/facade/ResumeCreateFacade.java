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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ResumeCreateFacade {

    private final ResumeService resumeService;
    private final CompanyService companyService;
    private final TechStackService techStackService;
    private final ResumePdfService resumePdfService;
    private final UserService userService;

    private final RedissonClient redissonClient;
    private final DistributedLockService distributedLockService;

    @Transactional
    public void createResume(CreateResumeRequest req, MultipartFile multipartFile) {
        User user = userService.getLoginUser();
        Long userId = user.getId();

        // 1) 락 획득
        RLock lock = distributedLockService.acquireLock(
                "resume-create-lock:" + userId,
                0,
                10,
                TimeUnit.SECONDS
        );
        // 락 획득 실패(null 반환) 시 BusinessException
        if (lock == null) {
            throw new BusinessException(ErrorCode.DUPLICATE_REQUEST);
        }

        try {
            // 2) 1분 내 재등록 제한 검사
            RBucket<Long> lastCreatedBucket = redissonClient.getBucket("resume:lastCreated:" + userId);
            Long lastCreatedTime = lastCreatedBucket.get();

            if (lastCreatedTime != null &&
                    (System.currentTimeMillis() - lastCreatedTime) < 60_000) {
                throw new BusinessException(ErrorCode.TOO_OFTEN_REQUEST);
            }

            // 3) 기존 이력서 등록 로직
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

            // 4) “최근 등록 시각” 기록 (1분 TTL)
            lastCreatedBucket.set(System.currentTimeMillis(), 60, TimeUnit.SECONDS);

        } finally {
            // 5) 락 해제
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
