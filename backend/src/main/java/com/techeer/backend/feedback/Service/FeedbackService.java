package com.techeer.backend.feedback.Service;

import com.techeer.backend.feedback.Repository.FeedbackRepository;
import com.techeer.backend.feedback.Repository.ResumeRepository;
import com.techeer.backend.feedback.entity.Feedback;
import com.techeer.backend.feedback.entity.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    public Feedback createFeedback(Long resumeId, String content, Double xCoordinate, Double yCoordinate) {
        Optional<Resume> resumeOptional = resumeRepository.findById(resumeId);
        if(resumeOptional.isEmpty()) {
            throw new IllegalArgumentException("Resume not found");
        }

        Resume resume = resumeOptional.get();
        Feedback feedback = new Feedback();
        feedback.setResume(resume);
        feedback.setContent(content);
        feedback.setxCoordinate(xCoordinate);
        feedback.setyCoordinate(yCoordinate);

        return feedbackRepository.save(feedback);
    }
}
