package com.techeer.backend.api.resume.service;

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
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.dto.request.CreateResumeRequest;
import com.techeer.backend.api.resume.dto.request.ResumeSearchRequest;
import com.techeer.backend.api.resume.dto.response.FetchResumeContentResponse;
import com.techeer.backend.api.resume.dto.response.ResumePageElement;
import com.techeer.backend.api.resume.dto.response.ResumePageResponse;
import com.techeer.backend.api.resume.dto.response.ResumeResponse;
import com.techeer.backend.api.resume.repository.GetResumeRepository;
import com.techeer.backend.api.resume.repository.ResumeRepository;
import com.techeer.backend.api.resume.repository.ResumeSpecification;
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
	private final ResumeConverter resumeConverter;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Transactional
	public void createResume(CreateResumeRequest req, MultipartFile resumePdf) throws IOException {

		Resume resume = Resume.of(req);

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

		resumeRepository.save(resume);
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
		return FetchResumeContentResponse.from(resume, feedbacks);
	}

	public List<ResumeResponse> searchResumesByUserName(String userName) {
		List<Resume> resumes = resumeRepository.findByUsername(userName);
		return resumes.stream()
			.map(resumeConverter::from)
			.toList();
	}

	// 태그 조회
	@Transactional(readOnly = true)
	public List<ResumeResponse> searchByTages(ResumeSearchRequest req, Pageable pageable) {
		Specification<Resume> spec = ResumeSpecification.search(req);
		Page<Resume> allActiveResumes = getResumeRepository.findAllActiveResumes(spec, pageable);
		return allActiveResumes.stream()
			.map(resumeConverter::from)
			.collect(Collectors.toList());
	}

	public ResumePageResponse getResumePage(Pageable pageable) {
		Page<Resume> resumes = resumeRepository.findAll(pageable);
		List<ResumePageElement> elements = resumes.getContent().stream()
			.map(ResumePageElement::of)
			.toList();

		return ResumePageResponse.from(elements, resumes);
	}
}
