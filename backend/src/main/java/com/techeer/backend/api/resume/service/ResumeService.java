package com.techeer.backend.api.resume.service;

import static com.techeer.backend.api.resume.converter.ResumeConverter.toResumePageResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.techeer.backend.api.feedback.domain.Feedback;
import com.techeer.backend.api.feedback.repository.FeedbackRepository;
import com.techeer.backend.api.resume.converter.ResumeConverter;
import com.techeer.backend.api.resume.domain.Company;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.dto.request.CreateResumeRequest;
import com.techeer.backend.api.resume.dto.request.ResumeSearchRequest;
import com.techeer.backend.api.resume.dto.response.FetchResumeContentResponse;
import com.techeer.backend.api.resume.dto.response.ResumePageResponse;
import com.techeer.backend.api.resume.dto.response.ResumeResponse;
import com.techeer.backend.api.resume.repository.CompanyRepository;
import com.techeer.backend.api.resume.repository.GetResumeRepository;
import com.techeer.backend.api.resume.repository.ResumeRepository;
import com.techeer.backend.api.resume.repository.ResumeSpecification;
import com.techeer.backend.api.resume.repository.TechStackRepository;
import com.techeer.backend.global.error.exception.NotFoundException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeService {
	private final AmazonS3 amazonS3;
	private final ResumeRepository resumeRepository;
	private final FeedbackRepository feedbackRepository;
	private final GetResumeRepository getResumeRepository;
	private final TechStackRepository techStackRepository;
	private final ResumeTechStackService resumeTechStackService;
	private final TechStackService techStackService;
	private final CompanyRepository companyRepository;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Transactional
	public void createResume(CreateResumeRequest req, MultipartFile resumePdf) throws IOException {

		Resume resume = ResumeConverter.toResume(req);

		resumeRepository.save(resume);

		List<String> companies = req.getApplyingCompanies();
		companies.stream()
			.map(companyName -> companyRepository.findByName(companyName)
				.orElseGet(() -> companyRepository.save(Company.builder()
					.name(companyName)
					.build())))
			.forEach(company -> {
				resume.addCompany(company);
				company.addResume(resume);  // 양방향 관계 설정 보장
			});

		// 변경 사항 저장
		// resumeRepository.save(resume);

		List<String> techStackNames = req.getTechStacks();

		techStackNames.stream()
			.map(techStack -> techStackRepository.findByName(techStack)
				.orElseGet(() -> techStackService.saveTechStack(techStack)))
			.forEach((techStack -> resumeTechStackService.saveResumeTechStack(resume, techStack)));

		String pdfName = resumePdf.getOriginalFilename();
		String s3PdfName = UUID.randomUUID().toString().substring(0, 10) + "_" + pdfName;

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(resumePdf.getSize());
		metadata.setContentType(resumePdf.getContentType());

		amazonS3.putObject(bucket, "resume/" + s3PdfName, resumePdf.getInputStream(), metadata);
		String resumeUrl = amazonS3.getUrl(bucket, "resume/" + s3PdfName).toString();
		resume.updateUrl(resumeUrl);

		// S3 정보 및 URL 업데이트 (bucketName, key, url)
		resume.updateS3Url(resumeUrl, bucket, "resume/" + s3PdfName);

	}

	//todo 피드백까지 생기면
	@Transactional(readOnly = true)
	public FetchResumeContentResponse getResumeContent(Long resumeId) throws IOException {
		// 이력서 찾기
		Resume resume = resumeRepository.findById(resumeId)
			.orElseThrow(NotFoundException::new);

		// 이력서의 피드백 찾기
		List<Feedback> feedbacks = feedbackRepository.findAllByResumeId(resumeId);

		// FetchResumeContentResponse 객체 생성 후 반환
		return ResumeConverter.toFetchResumeContentResponse(resume, feedbacks);
	}

	public List<ResumeResponse> searchResumesByUserName(String userName) {
		List<Resume> resumes = resumeRepository.findByUsername(userName);
		return resumes.stream()
			.map(ResumeConverter::toResumeResponse)
			.toList();
	}

	// 태그 조회
	@Transactional(readOnly = true)
	public List<ResumeResponse> searchByTages(ResumeSearchRequest req, Pageable pageable) {
		Specification<Resume> spec = ResumeSpecification.search(req);
		Page<Resume> allActiveResumes = getResumeRepository.findAllActiveResumes(spec, pageable);
		return allActiveResumes.stream()
			.map(ResumeConverter::toResumeResponse)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<ResumePageResponse> getResumePage(Pageable pageable) {
		// 페이지네이션을 적용하여 레포지토리에서 데이터를 가져옴
		Page<Resume> resumes = resumeRepository.findAll(pageable);

		// 첫 번째 Resume 객체를 가져옴 (예시로 첫 번째 요소를 변환)
		// 여러 Resume 객체를 페이지로 처리하려면 추가 로직 필요
		Resume resume = resumes.getContent().isEmpty() ? null : resumes.getContent().get(0);

		// Resume가 없을 경우 빈 결과를 처리
		if (resume == null) return null;

		List<ResumePageResponse> resumePageResponses = resumes.stream()
			.map(res -> toResumePageResponse(res, resumes))
			.collect(Collectors.toList());
		// Resume와 페이지 정보를 바탕으로 ResumePageResponse로 변환
		return resumePageResponses;
	}
}
