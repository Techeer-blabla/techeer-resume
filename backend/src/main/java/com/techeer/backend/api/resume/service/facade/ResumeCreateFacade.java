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

    // 추가: RedissonClient, DistributedLockService 의존성
    private final RedissonClient redissonClient;
    private final DistributedLockService distributedLockService;

    @Transactional
    public void createResume(CreateResumeRequest req, MultipartFile multipartFile) {
        // 현재 로그인 유저
        User user = userService.getLoginUser();
        Long userId = user.getId();

        // 1) 분산 락 획득 (동시에 들어오는 요청 충돌 방지)
        RLock lock = distributedLockService.acquireLock(
                "resume-create-lock:" + userId, // 락 키
                0,           // waitTime (대기 시간)
                10,          // leaseTime (점유 시간: 자동 해제)
                TimeUnit.SECONDS
        );

        // 획득 실패 시 null 반환 (기존 DistributedLockService 로직)
        if (lock == null) {
            throw new IllegalStateException("다른 요청이 이미 처리 중입니다. 잠시 후 다시 시도해주세요.");
        }

        try {
            // 2) 1분 내 재등록 제한 로직
            RBucket<Long> lastCreatedBucket = redissonClient.getBucket("resume:lastCreated:" + userId);
            Long lastCreatedTime = lastCreatedBucket.get();

            // 마지막 등록시각 존재 & 1분(60초) 이내라면 예외
            if (lastCreatedTime != null &&
                    (System.currentTimeMillis() - lastCreatedTime) < 60_000) {
                throw new IllegalStateException("1분 내에는 이력서를 다시 등록할 수 없습니다.");
            }

            // 3) 기존 이력서 생성 로직 --------------------------------
            // (a) 태그, 회사 조회/생성
            List<TechStack> techStacks = techStackService.findOrCreateTechStacks(req.getTechStackNames());
            List<Company> companies = companyService.findOrCreateCompanies(req.getCompanyNames());

            // (b) 최신 이력서(이전에 등록된 이력서) 조회
            Resume previousResume = resumeService.findLatestByUser(user);

            // (c) 새로운 Resume 엔티티 생성
            Resume resume = Resume.builder()
                    .user(user)
                    .position(req.getPosition())
                    .career(req.getCareer())
                    .name("Resume of " + user.getUsername() + " - "
                            + LocalDate.now(ZoneId.of("Asia/Seoul")))
                    .previousResumeId(previousResume != null ? previousResume.getId() : null)
                    .build();
            resumeService.saveResume(resume);

            if (previousResume != null) {
                previousResume.updateLaterResumeId(resume.getId());
            }

            // (d) 관계 설정 (ResumeTechStack, ResumeCompany)
            addResumeTechStacks(resume, techStacks);
            addResumeCompanies(resume, companies);

            // (e) PDF 저장
            ResumePdf resumePdf = resumePdfService.saveResumePdf(resume, multipartFile);
            resume.addResumePdf(resumePdf);

            // (f) 유저 객체에 이력서 추가
            user.addResume(resume);
            // ---------------------------------------------------

            // 4) 등록 성공 -> "최근 등록 시각" 갱신, TTL = 60초
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
