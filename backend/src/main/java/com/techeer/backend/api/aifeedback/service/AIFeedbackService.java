package com.techeer.backend.api.aifeedback.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.techeer.backend.api.aifeedback.domain.AIFeedback;
import com.techeer.backend.api.aifeedback.dto.AIFeedbackResponse;
import com.techeer.backend.api.aifeedback.repository.AIFeedbackRepository;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.repository.ResumeRepository;
import jakarta.transaction.Transactional;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class AIFeedbackService {

    private final AIFeedbackRepository aiFeedbackRepository;
    private final ResumeRepository resumeRepository;  // 이력서 데이터를 가져오기 위한 레포지토리 추가
    private final AmazonS3 amazonS3;
    private final OpenAIService openAiService;

    public AIFeedbackService(AIFeedbackRepository aiFeedbackRepository, ResumeRepository resumeRepository, AmazonS3 amazonS3, OpenAIService openAiService) {
        this.aiFeedbackRepository = aiFeedbackRepository;
        this.resumeRepository = resumeRepository;
        this.amazonS3 = amazonS3;
        this.openAiService = openAiService;
    }

    // PDF 파일을 S3에서 읽어온 뒤 텍스트로 변환하고, GPT로 피드백을 요청한 후, 결과를 저장하는 메서드
    @Transactional
    public AIFeedbackResponse generateAIFeedbackFromS3(Long resumeId) throws Exception {
        // 1. 이력서 정보 데이터베이스에서 조회
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 resumeId에 대한 이력서를 찾을 수 없습니다."));

        // 2. S3에서 PDF 파일 가져오기
        InputStream pdfInputStream = getPdfFileFromS3(resume.getS3BucketName(), resume.getS3Key());

        // 3. PDF 파일을 텍스트로 변환
        String resumeText = extractTextFromPdf(pdfInputStream);

        // 4. OpenAI GPT API 호출을 통한 피드백 생성
        String feedback = openAiService.getFeedback(resumeText);

        // 5. AIFeedback 엔티티 생성 및 저장
        AIFeedback aiFeedback = AIFeedback.builder()
                .resumeId(resumeId)
                .feedback(feedback)
                .build();

        AIFeedback savedFeedback = aiFeedbackRepository.save(aiFeedback);

        return AIFeedbackResponse.of(savedFeedback);
    }

    // S3에서 PDF 파일을 가져오는 메서드
    private InputStream getPdfFileFromS3(String bucketName, String key) {
        S3Object s3Object = amazonS3.getObject(bucketName, key);
        return s3Object.getObjectContent();
    }

    // PDF 파일을 텍스트로 변환하는 메서드
    private String extractTextFromPdf(InputStream inputStream) throws IOException {
        PDDocument document = PDDocument.load(inputStream);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();
        return text;
    }
}
