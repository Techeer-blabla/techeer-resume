package com.techeer.backend.feedback.dto;

public class FeedbackResponse {

    private Long feedbackId;
    private Long resumeId;
    private String content;
    private Double xCoordinate;
    private Double yCoordinate;

    public FeedbackResponse(Long feedbackId, Long resumeId, String content, Double xCoordinate, Double yCoordinate){
        this.feedbackId = feedbackId;
        this.resumeId = resumeId;
        this.content = content;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(Double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(Double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
